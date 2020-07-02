package jewpigeon.apps.newgrounds.Views.DashboardData.DashGridItems;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.LruCache;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;



import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import jewpigeon.apps.newgrounds.R;


public class DashGridView extends View implements Target<Drawable> {

    private  StaticLayout DashTitle;
    private  StaticLayout DashAuthor;
    private  Drawable DashIcon;
    private  Drawable DashTextBackground;
    private final int ITEM_SIZE = (int) getResources().getDimension(R.dimen.dashboard_item_size);
    private final int TitleColor = ContextCompat.getColor(getContext(), R.color.colorFeaturedItemTitleText);
    private final int AuthorColor = ContextCompat.getColor(getContext(), R.color.colorFeaturedItemAuthorText);

    private static TextPaint DashTitlePainter;
    private static TextPaint DashAuthorPainter;
    private Drawable defIcon;





    {
        TextCache.INSTANCE.changeWidth(ITEM_SIZE);
        defIcon = ContextCompat.getDrawable(getContext(), R.drawable.ng_default_icon);
        defIcon.setBounds(0,0,ITEM_SIZE,ITEM_SIZE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public DashGridView(Context context) {
        super(context);
        establishState();
    }

    public DashGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        establishState();
    }

    public DashGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        establishState();
    }

    public DashGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        establishState();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) dp(108),(int) dp(108));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        DashIcon.draw(canvas);
        canvas.save();
        canvas.translate(0, (ITEM_SIZE/3) *2);
        DashTextBackground.draw(canvas);
        canvas.save();
        DashTitle.draw(canvas);
        canvas.translate(0, DashTitle.getHeight());
        DashAuthor.draw(canvas);
        canvas.save();
        canvas.translate(0,0);
        canvas.restore();
    }






    public void setDashItem(DashGridItem item){
        DashIcon = new ColorDrawable(Color.BLACK);
        DashTextBackground = new ColorDrawable(ContextCompat.getColor(getContext(), R.color.colorDashboardItemLabelBackground));
        if (item.getImage() != null){
            Glide
                    .with(getContext())
                    .load(item.getImage())
                    .into(this);
        }
        else{
            Glide
                    .with(getContext())
                    .load(defIcon)
                    .into(this);
        }
        DashIcon.setBounds(0, 0, ITEM_SIZE, (ITEM_SIZE*2)/3);


        DashTitle = TextCache.INSTANCE.titleLayoutFor(item.getTitle());
        DashAuthor = TextCache.INSTANCE.authorLayoutFor(item.getAuthor());

        DashTextBackground.setBounds(0,0,ITEM_SIZE,ITEM_SIZE/3);
        requestLayout();
        invalidate();
    }


    private void establishState(){
        DashTitlePainter = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        DashTitlePainter.setColor(TitleColor);
        DashTitlePainter.setTextSize(sp(13));

        DashAuthorPainter = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        DashAuthorPainter.setColor(AuthorColor);
        DashAuthorPainter.setTextSize(sp(10));

        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        this.setBackground(ContextCompat.getDrawable(getContext(), outValue.resourceId));
        this.setClickable(true);

    }
    private float sp(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    private float dp(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    @Override
    public void onLoadStarted(@Nullable Drawable placeholder) {
        try {
            DashIcon = placeholder;
            DashIcon.setBounds(0,0,ITEM_SIZE,(ITEM_SIZE*2)/3);
        }catch (NullPointerException e){
            DashIcon = defIcon;
        }

    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        try {
            DashIcon = errorDrawable;

            DashIcon.setBounds(0,0,ITEM_SIZE,(ITEM_SIZE*2)/3);
        }catch (NullPointerException e){
            DashIcon = new ColorDrawable(Color.GRAY);
        }
    }

    @Override
    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
        try {
            DashIcon = resource;
            DashIcon.setBounds(0,0, ITEM_SIZE, (ITEM_SIZE*2)/3);
        }catch (NullPointerException e){
            DashIcon = new ColorDrawable(Color.RED);
        }
        invalidate();
    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {
    }
    @Override
    public void getSize(@NonNull SizeReadyCallback cb) {
        cb.onSizeReady(ITEM_SIZE, ITEM_SIZE);
    }
    @Override
    public void removeCallback(@NonNull SizeReadyCallback cb) {

    }
    @Override
    public void setRequest(@Nullable Request request) {

    }
    @Nullable
    @Override
    public Request getRequest() {
        return null;
    }
    @Override
    public void onStart() {

    }
    @Override
    public void onStop() {

    }
    @Override
    public void onDestroy() {

    }

    @Override
    protected boolean verifyDrawable(@NonNull Drawable who) {
        return super.verifyDrawable(who);
    }

    private enum TextCache {
        INSTANCE;

        private int width;
        private final LruCache<CharSequence, StaticLayout> titleCache = new LruCache<CharSequence, StaticLayout>(100) {
            @Override
            protected StaticLayout create(CharSequence key) {
                CharSequence titleEllipisized = TextUtils.ellipsize(key, DashTitlePainter, width, TextUtils.TruncateAt.END);
                return StaticLayout.Builder.obtain(titleEllipisized, 0, titleEllipisized.length(), DashTitlePainter, width)
                        .setAlignment(Layout.Alignment.ALIGN_CENTER)
                        .build();

            }

        };
        private final LruCache<CharSequence, StaticLayout> authorCache = new LruCache<CharSequence, StaticLayout>(100) {
            @Override
            protected StaticLayout create(CharSequence key) {
                return StaticLayout.Builder.obtain(key, 0, key.length(), DashAuthorPainter, width)
                        .setAlignment(Layout.Alignment.ALIGN_CENTER)
                        .build();
            }
        };

        public void changeWidth(int newWidth) {
            if (width != newWidth) {
                width = newWidth;
                titleCache.evictAll();
                authorCache.evictAll();
            }
        }

        public StaticLayout titleLayoutFor(CharSequence text) {
            return titleCache.get(text);
        }

        public StaticLayout authorLayoutFor(CharSequence text) {
            return authorCache.get(text);
        }
    }

}