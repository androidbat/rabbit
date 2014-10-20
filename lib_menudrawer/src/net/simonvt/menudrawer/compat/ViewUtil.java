package net.simonvt.menudrawer.compat;

import android.view.View.MeasureSpec;

public class ViewUtil {
	private static final int MEASURED_STATE_MASK = 0xff000000;
	private static final int MEASURED_STATE_TOO_SMALL = 0x01000000;
	 public static int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
	        int result = size;
	        int specMode = MeasureSpec.getMode(measureSpec);
	        int specSize =  MeasureSpec.getSize(measureSpec);
	        switch (specMode) {
	        case MeasureSpec.UNSPECIFIED:
	            result = size;
	            break;
	        case MeasureSpec.AT_MOST:
	            if (specSize < size) {
	                result = specSize | MEASURED_STATE_TOO_SMALL;
	            } else {
	                result = size;
	            }
	            break;
	        case MeasureSpec.EXACTLY:
	            result = specSize;
	            break;
	        }
	        return result | (childMeasuredState&MEASURED_STATE_MASK);
	    }
}
