package com.example.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BoxDrawingView extends View {
    int i=0,d=0;
    private static final String TAG = "BoxDrawingView";
    private Box mCurrentBox;
    private List<Box> mBoxen = new ArrayList<>();
    private Paint mBoxPaint;
    private Paint mBackgroundPaint;
    private String name="zzz";
    private int index=101;

    public BoxDrawingView(Context context) {
        super(context);
        setSaveEnabled(true);
    }

    public BoxDrawingView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        setSaveEnabled(true);
        // Paint the boxes a nice semitransparent red (ARGB)
        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);
// Paint the background off-white
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
// Fill the background
        d++;
       Log.d("draw","zz"+d);
        canvas.drawPaint(mBackgroundPaint);
        for (Box box : mBoxen) {
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);
            canvas.drawRect(left, top, right, bottom, mBoxPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());
        String action = "";
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                action = "ACTION_DOWN";
// Reset drawing state
                mCurrentBox = new Box(current);
                mBoxen.add(mCurrentBox);
                break;
            case MotionEvent.ACTION_MOVE:
                action = "ACTION_MOVE";
                if (mCurrentBox != null) {
                    mCurrentBox.setCurrent(current);
                   invalidate();

                    i++;
                    Log.d("invalidit","zz"+i);
                }
                break;
            case MotionEvent.ACTION_UP:
                action = "ACTION_UP";
                mCurrentBox = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                action = "ACTION_CANCEL";
                mCurrentBox = null;
                break;
        }
        Log.i(TAG, action + " at x=" + current.x +
                ", y=" + current.y);
        return true;
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        // Obtain any state that our super class wants to save.
        Parcelable superState = super.onSaveInstanceState();

        // Wrap our super class's state with our own.
        SavedState myState = new SavedState(superState);
        myState.name = this.name;
        myState.index = this.index;

        // Return our state along with our super class's state.
        return myState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        // Cast the incoming Parcelable to our custom SavedState. We produced
        // this Parcelable before, so we know what type it is.
        SavedState savedState = (SavedState) state;

        // Let our super class process state before we do because we should
        // depend on our super class, we shouldn't imply that our super class
        // might need to depend on us.
        super.onRestoreInstanceState(savedState.getSuperState());

        // Grab our properties out of our SavedState.
        this.name = savedState.name;
        this.index = savedState.index;

        // Update our visuals in whatever way we want, like...
        requestLayout(); //...or...
        invalidate(); //...or...
      //  doSomethingCustom();
    }
    private static class SavedState extends BaseSavedState {
        String name;
        int index;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            name = in.readString();
            index = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(name);
            out.writeInt(index);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
//private boolean someState;
//
//
//
//    public boolean isSomeState() {
//        return someState;
//    }
//
//    public void setSomeState(boolean someState) {
//        this.someState = someState;
//    }
//
//    @Override protected Parcelable onSaveInstanceState() {
//        final Parcelable superState = super.onSaveInstanceState();
//        final CustomViewSavedState customViewSavedState = new CustomViewSavedState(superState);
//        customViewSavedState.someState = this.someState;
//        return customViewSavedState;
//    }
//
//    @Override protected void onRestoreInstanceState(Parcelable state) {
//        final CustomViewSavedState customViewSavedState = (CustomViewSavedState) state;
//        setSomeState(customViewSavedState.someState);
//        super.onRestoreInstanceState(customViewSavedState.getSuperState());
//    }
//
//    private static class CustomViewSavedState extends BaseSavedState {
//
//        boolean someState;
//
//        public static final Parcelable.Creator<CustomViewSavedState> CREATOR = new Creator<CustomViewSavedState>() {
//            @Override public CustomViewSavedState createFromParcel(Parcel source) {
//                return new CustomViewSavedState(source);
//            }
//
//            @Override public CustomViewSavedState[] newArray(int size) {
//                return new CustomViewSavedState[size];
//            }
//        };
//
//        public CustomViewSavedState(Parcelable superState) {
//            super(superState);
//        }
//
//        private CustomViewSavedState(Parcel source) {
//            super(source);
//            someState = source.readInt() == 1;
//        }
//
//        @Override public void writeToParcel(Parcel out, int flags) {
//            super.writeToParcel(out, flags);
//            out.writeInt(someState ? 1 : 0);
//        }
//    }

}

