package com.example.accessibilityserviceappv2;

import android.accessibilityservice.AccessibilityService;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.text.Html;
import android.util.Log;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import java.util.List;

public class MyAccessibilityServiceV2 extends AccessibilityService {

    FrameLayout mLayout;
    LinearLayout lLayout;
    private static final String LOG_TAG = "MyActivity";
    public static String appname = "DummyApp";
    private Paint paint;
    Rect rectArray[];
    List<Rect> rectList = new ArrayList<>();
    int btnCounter;
    View view;
    TextView floatingView;
    String buttonClickTextTest;
    Boolean viewIsSet = false;



    @Override
    public void onAccessibilityEvent(AccessibilityEvent e) {

        btnCounter = 1;

        //Log.i("Accessibility", "onAccessibilityEvent");


        if(e.getPackageName()!=null){appname = e.getPackageName().toString();}

        String logString = "";

        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();

        if (e.getPackageName()!=null && e.getPackageName().toString().equals("com.example.emptytestapp")) {

            showFloatingWindow("init text");




            switch (e.getEventType()) {
                case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED: {

                    logNodeHierarchy(getRootInActiveWindow(), 0);
                }

                case AccessibilityEvent.TYPE_VIEW_CLICKED: {
                    //logString += "Text: " + nodeInfo.getText() + " \n " + " Content-Description: " + nodeInfo.getContentDescription() + "\n App Name: " + appname;
                    //Log.v(LOG_TAG, logString);

                }

                case AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED: {
                    //  logString += "Context Text: " + nodeInfo.getText() + " " + " Content-Description: " + nodeInfo.getContentDescription() + " App Name: " + appname;
                    //  Log.v(LOG_TAG, logString);
                }

            }

            //for (int i = 0 ; i < rectList.size() ; i++)
            //  Log.d("value is" , rectList.get(i).toString());

        }

        /*
        String name;

        if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            if (event.getPackageName().toString().equals("com.whatsapp")){
                StringBuilder message = new StringBuilder();
                if (!event.getText().isEmpty()) {
                    for (CharSequence subText : event.getText()) {
                        message.append(subText);
                    }
                    if (message.toString().contains("Message from")){
                        name=message.toString().substring(13);
                    }
                }
            }
        }


        if (event.getEventType()==AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED){
            if (source.getPackageName().equals("com.whatsapp")) {
                AccessibilityNodeInfo currentNode=getRootInActiveWindow();
                if (currentNode!=null && currentNode.getClassName().equals("android.widget.FrameLayout") && currentNode.getChild(2)!=null && currentNode.getChild(2).getClassName().equals("android.widget.TextView") && currentNode.getChild(2).getContentDescription().equals("Search")) {
                    currentNode.getChild(2).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }

         */



       /*==========  https://stackoverflow.com/questions/35842762/how-to-read-window-content-using-accessibilityservice-and-evoking-ui-using-dra ===========
       AccessibilityNodeInfo source = event.getSource();
        if (source == null) {return;}

        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId = source.findAccessibilityNodeInfosByViewId("YOUR PACKAGE NAME:id/RESOURCE ID FROM WHERE YOU WANT DATA");
        if (findAccessibilityNodeInfosByViewId.size() > 0) {
            AccessibilityNodeInfo parent = (AccessibilityNodeInfo) findAccessibilityNodeInfosByViewId.get(0);
            String requiredText = parent.getText().toString();
            Log.i("Required Text", requiredText);}
        ======================================================*/

    }

    @Override
    public void onInterrupt() {

        Log.i("Interrupt", "Interrupt");
        Toast.makeText(getApplicationContext(), "onInterrupt", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onServiceConnected() {

        super.onServiceConnected();
        Log.i("Service", "Connected");
        Toast.makeText(getApplicationContext(), "onServiceConnected", Toast.LENGTH_SHORT).show();


/*        System.out.println("onServiceConnected");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
        info.eventTypes=AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
        info.notificationTimeout = 100;
        info.packageNames = null;
        setServiceInfo(info);*/
    }


    public void logNodeHierarchy(AccessibilityNodeInfo nodeInfo, int depth) {

        Rect rect = new Rect();

        String contentDescription;
        String viewText;
        String hintText;



        if (nodeInfo == null) return;


        String logString = "";

        for (int i = 0; i < depth; ++i) {
            logString += " ";
        }

        nodeInfo.getBoundsInScreen(rect);

        Context context = getApplicationContext();

        logString += "\nElement: " + btnCounter + "\n Text: " + nodeInfo.getText() + "\n" + " Content-Description: " + nodeInfo.getContentDescription() + "\n App Name: " + appname + "\n Koordinaten " +rect + "\n Hint " + nodeInfo.getHintText() + "\n Labeled By " + nodeInfo.getLabeledBy();

        Log.v(LOG_TAG, logString);

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        mLayout = new FrameLayout(this);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        lp.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
        lp.format = PixelFormat.TRANSLUCENT;
        lp.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
       // lp.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //lp.alpha = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        lp.gravity = Gravity.TOP | Gravity.RIGHT;
        lp.x = rect.centerX();
        lp.y = rect.centerY();



        if(nodeInfo.getText()!=null){
            viewText = nodeInfo.getText().toString();
        }
        else {
            viewText = "empty";
        }

        if(nodeInfo.getContentDescription()!=null){
            contentDescription = nodeInfo.getContentDescription().toString();
        }
        else {
            contentDescription = "empty";
        }

        if(nodeInfo.getHintText()!=null){
            hintText = nodeInfo.getHintText().toString();
        }
        else {
            hintText = "empty";
        }

        CustomButton testBtn = new CustomButton(context, btnCounter, viewText, contentDescription, hintText);
        Button testBtn2 = new Button(context);
        testBtn.setText(String.valueOf(btnCounter));
        testBtn.setContentDescription("auto button");
        testBtn2.setText(String.valueOf(btnCounter));
        testBtn2.setContentDescription("auto button");
        //testBtn.setBackgroundColor(Color.RED);
        testBtn.setWidth(150);
        testBtn.setHeight(150);
        testBtn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        testBtn.setPadding(20,30,20,20);

        ShapeDrawable shapedrawable = new ShapeDrawable();
        shapedrawable.setShape(new RectShape());
        shapedrawable.getPaint().setColor(Color.BLACK);
        shapedrawable.getPaint().setStrokeWidth(10f);
        shapedrawable.getPaint().setStyle(Paint.Style.STROKE);
        testBtn.setBackground(shapedrawable);

        testBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClickTextTest = testBtn.showInformation();
                showFloatingWindow(buttonClickTextTest);
            }
        });

        wm.addView(testBtn, lp);

        btnCounter++;


        for (int i = 0; i < nodeInfo.getChildCount(); ++i) {

            logNodeHierarchy(nodeInfo.getChild(i), depth + 1);

        }


    }

    public void showFloatingWindow(String initText){
        Context context = getApplicationContext();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        lLayout = new LinearLayout(this);

        if(viewIsSet){
            wm.removeView(view);
        }

        Button testButton = new Button(context);

        LinearLayout.LayoutParams llParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        WindowManager.LayoutParams lp2 = new WindowManager.LayoutParams();


        lp2.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
        lp2.format = PixelFormat.TRANSLUCENT;
        lp2.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // lp.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        lp2.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp2.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp2.alpha = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        lp2.gravity = Gravity.BOTTOM;
        lp2.x = 0;
        lp2.y = 0;


        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.floatingwindow, null);

        TextView item = (TextView) view.findViewById(R.id.textView2);

        item.setText(Html.fromHtml(initText));

        lLayout.setLayoutParams(llParameters);

        view.setOnTouchListener(new View.OnTouchListener() {

            private WindowManager.LayoutParams updateParameters = lp2;
            int x, y;
            float touchedX, touchedY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        y = updateParameters.y;


                        Toast.makeText(getApplicationContext(), "onTouch", Toast.LENGTH_SHORT).show();


                        touchedY = event.getRawY();

                        break;

                    case MotionEvent.ACTION_MOVE:
                        updateParameters.y = (int) (y - (event.getRawY() - touchedY));

                        wm.updateViewLayout(view, updateParameters);

                    default:

                        break;
                }

                return false;
            }
        });


        wm.addView(view,lp2);

        viewIsSet = true;

    }

}
