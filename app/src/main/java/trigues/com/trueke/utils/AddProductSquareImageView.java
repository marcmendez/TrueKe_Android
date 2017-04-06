package trigues.com.trueke.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by mbaque on 06/04/2017.
 */

public class AddProductSquareImageView extends android.support.v7.widget.AppCompatImageView {
    public AddProductSquareImageView(Context context) {
        super(context);
    }

    public AddProductSquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AddProductSquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
