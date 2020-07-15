package jewpigeon.apps.newgrounds.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import jewpigeon.apps.newgrounds.R;


public class Dashboard extends MaterialCardView {
    private ConstraintLayout Label;
    private TextView LabelName;
    private ImageView LabelIcon;
    private LinearLayout ButtonPanelLayout;


    private int COLOR_BD = ContextCompat.getColor(getContext(), R.color.colorDashboardIconBackground);
    private int COLOR_BP = ContextCompat.getColor(getContext(), R.color.colorAccent);
    private ColorStateList BUTTON_STATE_LIST = new ColorStateList(new int[][]{
            new int[]{android.R.attr.state_pressed},
            new int[]{}
    },
            new int[]{
                    COLOR_BP,
                    COLOR_BD



            });


    private ColorStateList BUTTON_TEXT_STATE_LIST = new ColorStateList(new int[][]{
            new int[]{android.R.attr.state_pressed},
            new int[]{}
    },
            new int[]{
                    COLOR_BD,
                    COLOR_BP
            });


    public Dashboard(@NonNull Context context) {
        super(context);
        establishComponents(context, null);

    }

    public Dashboard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        establishComponents(context, attrs);

    }

    public Dashboard(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        establishComponents(context, attrs);

    }

    @SuppressLint("RestrictedApi")
    private void establishComponents(Context context, @Nullable AttributeSet attributeSet) {
        inflate(getContext(), R.layout.dashboard, this);
        Label = this.findViewById(R.id.dashboard_label);
        LabelName = this.findViewById(R.id.dashboard_label_name);
        ButtonPanelLayout = this.findViewById(R.id.dashboard_buttons_panel);
        LabelIcon = this.findViewById(R.id.dashboard_label_icon);


        TypedArray attributes = context.obtainStyledAttributes(attributeSet, R.styleable.Dashboard, 0, 0);
        try {
            boolean labelVisible = attributes.getBoolean(R.styleable.Dashboard_labelVisible, true);
            if (!labelVisible) removeLabel();
            String labelName = attributes.getString(R.styleable.Dashboard_labelText);
            LabelName.setText(labelName == null ? getResources().getString(R.string.LabelNameDefault) : labelName);
            Drawable labelIcon = attributes.getDrawable(R.styleable.Dashboard_labelIcon);
            if (labelIcon != null) LabelIcon.setImageDrawable(labelIcon);
            int buttonsMenuId = attributes.getResourceId(R.styleable.Dashboard_PanelButtonsList, 0);

            if (buttonsMenuId != 0) {
                MenuInflater v = new MenuInflater(getContext());
                Menu buttonsMenu = new MenuBuilder(getContext());
                v.inflate(buttonsMenuId, buttonsMenu);

                establishButtons(buttonsMenu);
            }


        } finally {
            attributes.recycle();
        }
    }

    private void establishButtons(Menu menu) {
        Button[] buttons = new Button[menu.size()];
        for (int i = 0; i < menu.size(); i++) {
            buttons[i] = new Button(getContext());
            buttons[i].setIncludeFontPadding(false);

            buttons[i].setMinHeight(0);
            buttons[i].setMinimumHeight(0);

            buttons[i].setMinWidth(0);
            buttons[i].setMinimumWidth(0);

            buttons[i].setPadding(50, buttons[i].getPaddingTop(), 50, buttons[i].getPaddingBottom());

            buttons[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            buttons[i].setText(menu.getItem(i).getTitle());

            buttons[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.dashboard_icon_shape));
            buttons[i].setBackgroundTintList(BUTTON_STATE_LIST);

            buttons[i].setTextColor(BUTTON_TEXT_STATE_LIST);
            buttons[i].setTextAlignment(TEXT_ALIGNMENT_CENTER);
            buttons[i].setTextSize(11);
            buttons[i].setAllCaps(false);
            buttons[i].setClickable(true);

            this.ButtonPanelLayout.addView(buttons[i]);
        }
    }


    public void removeLabel() {
        this.Label.setVisibility(GONE);

    }

}
