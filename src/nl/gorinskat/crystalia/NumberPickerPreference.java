package nl.gorinskat.crystalia;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.preference.DialogPreference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Method;

public class NumberPickerPreference extends DialogPreference {

    private static final int DEFAULT_VALUE = 1;
    private static final int MINIMUM_VALUE = 1;
    private static final int MAXIMUM_VALUE = 999999;
    private static final int BUTTON_REPEAT_INTERVAL = 100; // ms

	private int valueMax, valueMin, valueInt;
    private String valueStr;

	private EditText editText;

    private Handler mHandler = new Handler();

    public NumberPickerPreference(Context context)
    {
        this(context, null);
    }

	public NumberPickerPreference(Context context, AttributeSet attrs) {
		super(context, attrs);

        // get attributes specified in XML
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.NumberPickerPreference, 0, 0);
        try
        {
            setMinValue(a.getInteger(R.styleable
                    .NumberPickerPreference_min,MINIMUM_VALUE));
            setMaxValue(a.getInteger(R.styleable
                    .NumberPickerPreference_android_max, MAXIMUM_VALUE));
        }
        finally
        {
            a.recycle();
        }

		setDialogLayoutResource(R.layout.dialog_number_picker);
		setPositiveButtonText(android.R.string.ok);
		setNegativeButtonText(android.R.string.cancel);
		setDialogIcon(null);
    }

    @Override
    protected void onSetInitialValue(boolean restore, Object defaultValue)
    {
        setValue(restore ? getPersistedString(String.valueOf(DEFAULT_VALUE))
                : String.valueOf(defaultValue));
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index)
    {
        return a.getInt(index, DEFAULT_VALUE);
    }


    private Runnable runnableIncrementValue = new Runnable()
    {
        public void run() {
            incrementValue();
            mHandler.postDelayed(this, BUTTON_REPEAT_INTERVAL);
        }
    };

    private Runnable runnableDecrementValue = new Runnable()
    {
        public void run() {
            decrementValue();
            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onBindDialogView(View view)
    {
        super.onBindDialogView(view);

        editText = (EditText) view.findViewById(R.id.number_picker_value);
        editText.setText(valueStr);

        editText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                // Update with new textvalue
                Log.d(CrystaliaActivity.APP_NAMESPACE,
                        "textvalue: "+ s.toString());
                setValue(String.valueOf(s));
                // TODO: check if textChange should be leading or button
                // press or both somehow ?!?!?!?
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        Button buttonIncrement = (Button) view.findViewById(R.id.button_plus);
        Button buttonDecrement = (Button) view.findViewById(R.id.button_minus);

        buttonIncrement.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View view, MotionEvent motionevent)
            {
                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {

                    incrementValue();

                    mHandler.removeCallbacks(runnableIncrementValue);
                    mHandler.postDelayed(runnableIncrementValue,
                            BUTTON_REPEAT_INTERVAL);

                } else if (action == MotionEvent.ACTION_UP) {

                    mHandler.removeCallbacks(runnableIncrementValue);
                }
                return false;
            }
        });

        buttonDecrement.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View view, MotionEvent motionevent)
            {
                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {

                    decrementValue();

                    mHandler.removeCallbacks(runnableDecrementValue);
                    mHandler.postDelayed(runnableDecrementValue, BUTTON_REPEAT_INTERVAL);

                } else if (action == MotionEvent.ACTION_UP) {

                    mHandler.removeCallbacks(runnableDecrementValue);
                }
                return false;
            }
        });
    }

    public int getMinValue()
    {
        return valueMin;
    }

    public void setMinValue(int minValue)
    {
        valueMin = minValue;
        setValue(Math.max(valueInt, valueMin));
    }

    public int getMaxValue()
    {
        return valueMax;
    }

    public void setMaxValue(int maxValue)
    {
        valueMax = maxValue;
        setValue(Math.min(valueInt, valueMax));
    }

    private int getValueInt() {
		return valueInt;
	}
    private String getValueString() {
        return valueStr;
    }

    private int checkBounds(int value) {
        return Math.max(Math.min(value, valueMax), valueMin);
    }

    public void setValue(String sValue)
    {
        if (sValue.length() <= 0) {
            sValue = String.valueOf(getMinValue());
        }

        // Convert to int
        int iValue = Integer.parseInt(sValue);

        // Force int withing bounds
        iValue = checkBounds(iValue);

        // Convert to string
        sValue = String.valueOf(iValue);

        if (sValue != valueStr)
        {
            valueStr = sValue;
            valueInt = iValue;
            persistString(sValue);
            notifyChanged();
        }
    }

    public void setValue(int iValue)
    {
        // Force int withing bounds
        iValue = checkBounds(iValue);

        // Convert to string
        String sValue = String.valueOf(iValue);

        if (sValue != valueStr)
        {
            valueStr = sValue;
            valueInt = iValue;
            persistString(sValue);
            notifyChanged();
        }
    }

    private void incrementValue() {
        // Increment value
        int value = getValueInt();
        value++;
        setValue(value);
        editText.setText(getValueString());
    }

    private void decrementValue() {
        // Increment value
        int value = getValueInt();
        value--;
        setValue(value);
        editText.setText(getValueString());
    }

    @Override
    protected void onDialogClosed(boolean positiveResult)
    {
        super.onDialogClosed(positiveResult);

        // when the user selects "OK", persist the new value
        if (positiveResult)
        {
            String textValue = editText.getText().toString();
           // int intValue = Integer.parseInt(textValue);
            if (callChangeListener(textValue))
            {
                setValue(textValue);
            }
        }
    }

    @Override
    protected Parcelable onSaveInstanceState()
    {
        // save the instance state so that it will survive screen orientation changes and other events that may temporarily destroy it
        final Parcelable superState = super.onSaveInstanceState();

        // set the state's value with the class member that holds current setting value
        final SavedState myState = new SavedState(superState);
        myState.valueMin = getMinValue();
        myState.valueMax = getMaxValue();
        myState.valueInt = getValueInt();

        return myState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state)
    {
        // check whether we saved the state in onSaveInstanceState()
        if (state == null || !state.getClass().equals(SavedState.class))
        {
            // didn't save the state, so call superclass
            super.onRestoreInstanceState(state);
            return;
        }

        // restore the state
        SavedState myState = (SavedState) state;
        setMinValue(myState.valueMin);
        setMaxValue(myState.valueMax);
        setValue(myState.valueInt);

        super.onRestoreInstanceState(myState.getSuperState());
    }

    private static class SavedState extends BaseSavedState
    {
        int valueMin, valueMax, valueInt;

        public SavedState(Parcelable superState)
        {
            super(superState);
        }

        public SavedState(Parcel source)
        {
            super(source);

            valueMin = source.readInt();
            valueMax = source.readInt();
            valueInt = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags)
        {
            super.writeToParcel(dest, flags);

            dest.writeInt(valueMin);
            dest.writeInt(valueMax);
            dest.writeInt(valueInt);
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>()
        {
            @Override
            public SavedState createFromParcel(Parcel in)
            {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size)
            {
                return new SavedState[size];
            }
        };
    }
}
