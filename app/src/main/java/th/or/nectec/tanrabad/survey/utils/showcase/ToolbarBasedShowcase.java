package th.or.nectec.tanrabad.survey.utils.showcase;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener;

public class ToolbarBasedShowcase implements Showcase {

    private final ShowcaseView.Builder showcaseBuilder;
    private OnShowcaseDismissListener onShowcaseDismissListener;

    public ToolbarBasedShowcase(Activity activity, @IdRes int toolbarId, @IdRes int viewId, boolean isShowOnlyOnce) {
        showcaseBuilder = BaseShowcase.build(activity)
                .setTarget(new ToolbarActionItemTarget((Toolbar) activity.findViewById(toolbarId), viewId))
                .setShowcaseEventListener(new SimpleShowcaseEventListener() {
                    @Override
                    public void onShowcaseViewHide(ShowcaseView showcaseView) {
                        super.onShowcaseViewHide(showcaseView);
                        if (onShowcaseDismissListener != null)
                            onShowcaseDismissListener.onDismissListener(showcaseView);
                    }
                });

        if (isShowOnlyOnce)
            showcaseBuilder.singleShot(toolbarId + viewId);
    }

    @Override
    public void setTitle(String title) {
        showcaseBuilder.setContentTitle(title);
    }

    @Override
    public void setMessage(String message) {
        showcaseBuilder.setContentText(message);
    }

    @Override
    public void display() {
        showcaseBuilder.build();
    }

    @Override
    public void setOnShowCaseDismissListener(OnShowcaseDismissListener onShowcaseDismissListener) {
        this.onShowcaseDismissListener = onShowcaseDismissListener;
    }
}
