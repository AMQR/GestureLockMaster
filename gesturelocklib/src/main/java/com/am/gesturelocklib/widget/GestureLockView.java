package com.am.gesturelocklib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.am.gesturelocklib.R;
import com.am.gesturelocklib.bean.GestueCircleBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by AM on 2016/4/29.
 */
public class GestureLockView extends View {
    private Paint paintNormal;
    private Paint paintOnTouch;  // 外圆
    private Paint paintInnerCycle; // 内圆
    private Paint paintLines;  // 划线
    private Paint paintKeyError;
    private GestueCircleBean[] cycles;
    private Path linePath = new Path();
    private List<Integer> linedCycles = new ArrayList<Integer>();
    private OnGestureFinishListener onGestureFinishListener;
    private String key = "";
    private int eventX, eventY;
    private boolean canContinue = true;
    private boolean result;
    private Timer timer;


    private boolean upDiyColor = false; // 释放后显示自定义颜色

    //以下9种颜色均对外提供设置颜色的方法
    private  int OUT_CYCLE_NORMAL_COLOR =getResources().getColor(R.color.deep_gray); // 外圆正常颜色
    private  int OUT_CYCLE_CHOOSE_COLOR =getResources().getColor(R.color.deep_bule);// 外圆选中颜色

    private  int INNER_CYCLE_NORMAL_COLOR =getResources().getColor(R.color.normal_gray); // 内圆正常颜色
    private  int INNER_CYCLE_CHOOSE_COLOR =getResources().getColor(R.color.litle_blue); // 内圆选中颜色
    private  int INNER_CYCLE_ERROR_COLOR = getResources().getColor(R.color.red); // 连接错误时内圆颜色

    private  int LINE_NORMAL_COLOR = getResources().getColor(R.color.ablue); // 连接线颜色
    private  int LINE_ERROR_COLOR = getResources().getColor(R.color.ared); // 连接错误醒目提示颜色


    private  int INNER_CYCLE_DIY_COLOR = getResources().getColor(R.color.ok_green); // 自定义 释放后内圆的颜色
    private  int LINE_DIY_COLOR = getResources().getColor(R.color.agreen); //  自定义 释放后外圆和连线的颜色



    public void setOnGestureFinishListener(
            OnGestureFinishListener onGestureFinishListener) {
        this.onGestureFinishListener = onGestureFinishListener;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public interface OnGestureFinishListener {
        public void OnGestureFinish(boolean success,String gestureCode);
    }

    public GestureLockView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public GestureLockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GestureLockView(Context context) {
        super(context);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int perSize = 0;
        if (cycles == null && (perSize = getWidth() / 6) > 0) {
            cycles = new GestueCircleBean[9];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    GestueCircleBean cycle = new GestueCircleBean();
                    cycle.setNum(i * 3 + j);
                    cycle.setOx(perSize * (j * 2 + 1));
                    cycle.setOy(perSize * (i * 2 + 1));
                    cycle.setR(perSize * 0.5f);
                    cycles[i * 3 + j] = cycle;
                }
            }
        }
    }

    private void init() {
        paintNormal = new Paint();
        paintNormal.setAntiAlias(true);
        paintNormal.setStrokeWidth(5);
        paintNormal.setStyle(Paint.Style.STROKE);

        paintOnTouch = new Paint();
        paintOnTouch.setAntiAlias(true);
        paintOnTouch.setStrokeWidth(10);
        paintOnTouch.setStyle(Paint.Style.STROKE);

        paintInnerCycle = new Paint();
        paintInnerCycle.setAntiAlias(true);
        paintInnerCycle.setStyle(Paint.Style.FILL);

        paintLines = new Paint();
        paintLines.setAntiAlias(true);
        paintLines.setStyle(Paint.Style.STROKE);
        paintLines.setStrokeWidth(25);

        paintKeyError = new Paint();
        paintKeyError.setAntiAlias(true);
        paintKeyError.setStyle(Paint.Style.STROKE);
        paintKeyError.setStrokeWidth(3);
    }

    @Override
    protected void onDraw(Canvas canvas) { // 我们在抬起的时候调用postInvalidate();结果会重新执行一遍onDraw
        super.onDraw(canvas);
        System.out.println("onDraw 执行");
        for (int i = 0; i < cycles.length; i++) {
            if (!canContinue && !result) {  // 不能再连接 而且 结果不正确
                if(upDiyColor){ // 如果释放后需要显示自定义的颜色
                    paintOnTouch.setColor(LINE_DIY_COLOR);
                    paintInnerCycle.setColor(INNER_CYCLE_DIY_COLOR);
                    paintLines.setColor(LINE_DIY_COLOR);
                }else{
                    paintOnTouch.setColor(LINE_ERROR_COLOR);
                    paintInnerCycle.setColor(INNER_CYCLE_ERROR_COLOR);
                    paintLines.setColor(LINE_ERROR_COLOR);
                }
            } else if (cycles[i].isOnTouch()) { // 如果该点已经连接
                paintOnTouch.setColor(OUT_CYCLE_CHOOSE_COLOR);
                paintInnerCycle.setColor(INNER_CYCLE_CHOOSE_COLOR);
                paintLines.setColor(LINE_NORMAL_COLOR);
            } else { // 该点可以连接
                paintNormal.setColor(OUT_CYCLE_NORMAL_COLOR);
                paintInnerCycle.setColor(INNER_CYCLE_CHOOSE_COLOR);
                paintLines.setColor(LINE_NORMAL_COLOR);
            }

            if (cycles[i].isOnTouch()) {
                if (canContinue || result) {
                    paintInnerCycle.setColor(INNER_CYCLE_CHOOSE_COLOR);
                }else {
                    if(upDiyColor){ // 释放后显示自定义的颜色
                        paintInnerCycle.setColor(INNER_CYCLE_DIY_COLOR);
                    }else{
                        paintInnerCycle.setColor(INNER_CYCLE_ERROR_COLOR);
                    }


                }
                canvas.drawCircle(cycles[i].getOx(), cycles[i].getOy(),
                        cycles[i].getR(), paintOnTouch);
                drawInnerBlueCycle(cycles[i], canvas);

            } else {
                paintInnerCycle.setColor(INNER_CYCLE_NORMAL_COLOR);
                canvas.drawCircle(cycles[i].getOx(), cycles[i].getOy(),
                        cycles[i].getR(), paintNormal);
                drawInnerBlueCycle(cycles[i], canvas);
            }
        }
        drawLine(canvas);
    }

    private void drawLine(Canvas canvas) {
        System.out.println("drawLine 执行");
        linePath.reset();
        if (linedCycles.size() > 0) {
            for (int i = 0; i < linedCycles.size(); i++) {
                int index = linedCycles.get(i);
                float x = cycles[index].getOx();
                float y = cycles[index].getOy();
                if (i == 0) {
                    linePath.moveTo(x,y);
                } else {
                    linePath.lineTo(x,y);
                }
            }
            if (canContinue) {
                linePath.lineTo(eventX, eventY);
            }else {
                linePath.lineTo(cycles[linedCycles.get(linedCycles.size()-1)].getOx(), cycles[linedCycles.get(linedCycles.size()-1)].getOy());
            }
            canvas.drawPath(linePath, paintLines);

        }
    }

    private void drawInnerBlueCycle(GestueCircleBean myCycle, Canvas canvas) {
        canvas.drawCircle(myCycle.getOx(), myCycle.getOy(), myCycle.getR() / 1.5f,
                paintInnerCycle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (canContinue) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE: {
                    eventX = (int) event.getX();
                    eventY = (int) event.getY();
                    for (int i = 0; i < cycles.length; i++) {
                        if (cycles[i].isPointIn(eventX, eventY)) {
                            cycles[i].setOnTouch(true);
                            if (!linedCycles.contains(cycles[i].getNum())) {
                                linedCycles.add(cycles[i].getNum());
                            }
                        }
                    }
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    // 暂停触碰
                    canContinue = false;
                    // 检查结果
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < linedCycles.size(); i++) {
                        sb.append(linedCycles.get(i));
                    }
                    result = key.equals(sb.toString());
                    if (onGestureFinishListener != null) {
                        onGestureFinishListener.OnGestureFinish(result,sb.toString());
                    }
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            // 还原
                            eventX = eventY = 0;
                            for (int i = 0; i < cycles.length; i++) {
                                cycles[i].setOnTouch(false);
                            }
                            linedCycles.clear();
                            linePath.reset();
                            canContinue = true;
                            postInvalidate();
                        }
                    }, 1000);
                    break;
                }
            }
            invalidate();
        }
        return true;
    }


    public boolean isUpDiyColor() {
        return upDiyColor;
    }

    // 是否开启释放后显示自定义颜色
    public void setUpDiyColor(boolean upDiyColor) {
        this.upDiyColor = upDiyColor;
    }

    public void setOUT_CYCLE_NORMAL_COLOR(int OUT_CYCLE_NORMAL_COLOR) {
        this.OUT_CYCLE_NORMAL_COLOR = OUT_CYCLE_NORMAL_COLOR;
    }

    public void setOUT_CYCLE_CHOOSE_COLOR(int OUT_CYCLE_CHOOSE_COLOR) {
        this.OUT_CYCLE_CHOOSE_COLOR = OUT_CYCLE_CHOOSE_COLOR;
    }

    public void setINNER_CYCLE_NORMAL_COLOR(int INNER_CYCLE_NORMAL_COLOR) {
        this.INNER_CYCLE_NORMAL_COLOR = INNER_CYCLE_NORMAL_COLOR;
    }

    public void setINNER_CYCLE_CHOOSE_COLOR(int INNER_CYCLE_CHOOSE_COLOR) {
        this.INNER_CYCLE_CHOOSE_COLOR = INNER_CYCLE_CHOOSE_COLOR;
    }

    public void setINNER_CYCLE_ERROR_COLOR(int INNER_CYCLE_ERROR_COLOR) {
        this.INNER_CYCLE_ERROR_COLOR = INNER_CYCLE_ERROR_COLOR;
    }

    public void setLINE_NORMAL_COLOR(int LINE_NORMAL_COLOR) {
        this.LINE_NORMAL_COLOR = LINE_NORMAL_COLOR;
    }

    public void setLINE_ERROR_COLOR(int LINE_ERROR_COLOR) {
        this.LINE_ERROR_COLOR = LINE_ERROR_COLOR;
    }

    public void setINNER_CYCLE_DIY_COLOR(int INNER_CYCLE_DIY_COLOR) {
        this.INNER_CYCLE_DIY_COLOR = INNER_CYCLE_DIY_COLOR;
    }

    public void setLINE_DIY_COLOR(int LINE_DIY_COLOR) {
        this.LINE_DIY_COLOR = LINE_DIY_COLOR;
    }

}
