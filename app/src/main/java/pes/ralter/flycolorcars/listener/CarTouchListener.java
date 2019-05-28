package pes.ralter.flycolorcars.listener;

import android.os.Build;
import android.view.MotionEvent;
import android.view.View;

public class CarTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                v.startDragAndDrop(null, dragShadowBuilder, v, 0);
            } else {
                v.startDrag(null, dragShadowBuilder, v, 0);
            }
            v.setVisibility(View.GONE);
            return true;
        } else {
            return false;
        }
    }
}
