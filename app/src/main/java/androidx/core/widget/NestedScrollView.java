package androidx.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class NestedScrollView extends View {
    public NestedScrollView(Context context) {
        this(context, null);
    }

    public NestedScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
