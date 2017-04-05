package com.example.qiqi.neq;

/**
 * Created by qiqi on 2016/11/14.
 */
public class Pending {

    private void checkPitch1Posture() {

        // variables
        //int deltaPitch = pitch1 - pitch2;
        int deltaPitch1 =  calculateDelta(getCalibratedPitch1(), pitch1);
        //  deltaPitch = (deltaPitch < 0) ? -deltaPitch : deltaPitch;
        //int deltaPitch = pitch1 - pitch2;
        System.out.print("deltaPitch1");
        System.out.println(deltaPitch1);

        // guard: check if the threshold is not exceeded //if so reset the timer to 0 and create no notification then return
        if (deltaPitch1 < BAD_PITCH1_THRESHOLD) {
            lastBadPitch1Time = 0;
            NotificationShownPitch1 = false;
            return;
        }

        // variables
        long currentMillis1 = System.currentTimeMillis();

        // guard: check if the timer is already set,
        if (lastBadPitch1Time <= 0) {
            System.out.println("Set last bad pitch1 time!");
            lastBadPitch1Time = currentMillis1;
            return;
        }

        // check if the timer is exceeded (the user is in a bad posture for longer than BAD_PITCH_TIME and the user needs a warning also check if not other notifications are active
        if (currentMillis1 - lastBadPitch1Time > BAD_PITCH1_TIME && !NotificationShownRoll1 && !NotificationShownPitch1&& !NotificationShownRoll2 && !NotificationShownPitch2) {

            // show notification
            System.out.println("Notification shown Pitch1!");
            MainActivity.showNotificationInMenu(context);
            NotificationShownPitch1 = true;
        }
    }
}
