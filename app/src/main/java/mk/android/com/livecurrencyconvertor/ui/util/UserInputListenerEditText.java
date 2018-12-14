package mk.android.com.livecurrencyconvertor.ui.util;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import mk.android.com.livecurrencyconvertor.ui.list.CurrencyUpdatedListener;
import mk.android.com.livecurrencyconvertor.util.Mappers;

/**
 * Responds only to user input and not any other set text methods
 */
public class UserInputListenerEditText extends AppCompatEditText {

    private int position;
    //onTextChange called due to settext methods and not user input on edit texts
    private boolean restrainListener = true;
    private CurrencyUpdatedListener currencyUpdatedListener;

    public UserInputListenerEditText(Context context) {
        super(context);
    }

    public UserInputListenerEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserInputListenerEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void updatePosition(int position) {
        this.position = position;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        restrainListener = true;
        super.setText(text, type);
        restrainListener = false;
    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i2, int i3) {

        if (!restrainListener) {
            //Only base currency amount can be changed
            if (position != 0) {
                clearFocus();
                return;
            }
            Double amount = Mappers.parseDouble(s.toString());
            currencyUpdatedListener.onAmountUpdated(amount);
        }
    }

    public void setAmountChangeListener(CurrencyUpdatedListener currencyUpdatedListener) {
        this.currencyUpdatedListener = currencyUpdatedListener;
    }
}
