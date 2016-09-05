package com.thinkcoo.mobile.presentation.views.component.mydayview;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.util.Log;
import com.thinkcoo.mobile.model.entity.ScheduleCreateUser;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by  Leevin
 * on 2016/7/10 ,23:03.
 */
public class Event implements Cloneable, Parcelable {

    private static final String TAG = "Event";

    private static String mNoTitleString;
    private static int mNoColorColor;
    public String scheduleId; // 主事件id
    public String runDate;// 参考用
    public boolean isCreateAuthor;
    public ScheduleCreateUser createdUser;
    public int scheduleType;
    public String circleId;
    public String easemobGroupId;

    public String id;  // eventTimeId
    public int color;
    public String title;
    public String location ;
    public boolean allDay;
    /***********************************************************************************************/
    public int startDay;       // start Julian day
    public int endDay;         // end Julian day
    public int startTime;      // Start and end time are in minutes since midnight
    public int endTime;

    public long startMillis;   // UTC milliseconds since the epoch
    public long endMillis;     // UTC milliseconds since the epoch
    private int mColumn;
    private int mMaxColumns;

    public boolean hasAlarm;
    public boolean isRepeating;

    // The coordinates of the event rectangle drawn on the screen.
    public float left;
    public float right;
    public float top;
    public float bottom;

    // These 4 fields are used for navigating among events within the selected
    // hour in the Day and Week view.
    public Event nextRight;
    public Event nextLeft;
    public Event nextUp;
    public Event nextDown;

    @Override
    public final Object clone() throws CloneNotSupportedException {
        super.clone();
        Event e = new Event();

        e.title = title;
        e.color = color;
        e.location = location;
        e.allDay = allDay;
        e.startDay = startDay;
        e.endDay = endDay;
        e.startTime = startTime;
        e.endTime = endTime;
        e.startMillis = startMillis;
        e.endMillis = endMillis;
        e.hasAlarm = hasAlarm;
        e.isRepeating = isRepeating;
        return e;
    }

    public final void copyTo(Event dest) {
        dest.id = id;
        dest.title = title;
        dest.color = color;
        dest.location = location;
        dest.allDay = allDay;
        dest.startDay = startDay;
        dest.endDay = endDay;
        dest.startTime = startTime;
        dest.endTime = endTime;
        dest.startMillis = startMillis;
        dest.endMillis = endMillis;
        dest.hasAlarm = hasAlarm;
        dest.isRepeating = isRepeating;
    }

    public static final Event newInstance() {
        Event e = new Event();
        e.id = null;
        e.title = null;
        e.color = 0;
        e.location = null;
        e.allDay = false;
        e.startDay = 0;
        e.endDay = 0;
        e.startTime = 0;
        e.endTime = 0;
        e.startMillis = 0;
        e.endMillis = 0;
        e.hasAlarm = false;
        e.isRepeating = false;
        return e;
    }

    public final void dump() {
        Log.e("Cal", "+----------------runDate-------------------------+"+runDate);
        Log.e("Cal", "+        id = " + id);
        Log.e("Cal", "+    scheduleId = " + scheduleId);
        Log.e("Cal", "+     color = " + color);
        Log.e("Cal", "+     title = " + title);
        Log.e("Cal", "+  location = " + location);
        Log.e("Cal", "+    allDay = " + allDay);
        Log.e("Cal", "+  startDay = " + startDay);
        Log.e("Cal", "+    endDay = " + endDay);
        Log.e("Cal", "+ startTime = " + startTime);
        Log.e("Cal", "+   endTime = " + endTime);
        Log.e("Cal", "+ startMillis = " + startMillis);
        Log.e("Cal", "+ endMillis = " + endMillis);
        Log.e("Cal", "+ isCreateAuthor = " + isCreateAuthor);
        Log.e("Cal", "+ mColumn = " + mColumn);
        Log.e("Cal", "+ mMaxColumns = " + mMaxColumns);
    }

    /**
     * Computes a position for each event.  Each event is displayed
     * as a non-overlapping rectangle.  For normal events, these rectangles
     * are displayed in separate columns in the week view and day view.  For
     * all-day events, these rectangles are displayed in separate rows along
     * the top.  In both cases, each event is assigned two numbers: N, and
     * Max, that specify that this event is the Nth event of Max number of
     * events that are displayed in a group. The width and position of each
     * rectangle depend on the maximum number of rectangles that occur at
     * the same time.
     *
     * @param eventsList the list of events, sorted into increasing time order
     * @param minimumDurationMillis minimum duration acceptable as cell height of each event
     * rectangle in millisecond. Should be 0 when it is not determined.
     */
    public static void computePositions(ArrayList<Event> eventsList,
                                               long minimumDurationMillis) {
        if (eventsList == null) {
            return;
        }
        // Compute the column positions separately for the all-day events
        doComputePositions(eventsList, minimumDurationMillis, false);
        doComputePositions(eventsList, minimumDurationMillis, true);
    }

    private static void doComputePositions(ArrayList<Event> eventsList,
                                           long minimumDurationMillis, boolean doAlldayEvents) {
        final ArrayList<Event> activeList = new ArrayList<Event>();
        final ArrayList<Event> groupList = new ArrayList<Event>();

        if (minimumDurationMillis < 0) {
            minimumDurationMillis = 0;
        }

        long colMask = 0;
        int maxCols = 0;
        for (Event event : eventsList) {
            // Process all-day events separately
            if (event.drawAsAllday() != doAlldayEvents)
                continue;

            if (!doAlldayEvents) {
                colMask = removeNonAlldayActiveEvents(
                        event, activeList.iterator(), minimumDurationMillis, colMask);
            } else {
                colMask = removeAlldayActiveEvents(event, activeList.iterator(), colMask);
            }

            // If the active list is empty, then reset the max columns, clear
            // the column bit mask, and empty the groupList.
            if (activeList.isEmpty()) {
                for (Event ev : groupList) {
                    ev.setMaxColumns(maxCols);
                }
                maxCols = 0;
                colMask = 0;
                groupList.clear();
            }

            // Find the first empty column.  Empty columns are represented by
            // zero bits in the column mask "colMask".
            int col = findFirstZeroBit(colMask);
            if (col == 64)
                col = 63;
            colMask |= (1L << col);
            event.setColumn(col);
            activeList.add(event);
            groupList.add(event);
            int len = activeList.size();
            if (maxCols < len)
                maxCols = len;
        }
        for (Event ev : groupList) {
            ev.setMaxColumns(maxCols);
        }
    }

    private static long removeAlldayActiveEvents(Event event, Iterator<Event> iter, long colMask) {
        // Remove the inactive allday events. An event on the active list
        // becomes inactive when the end day is less than the current event's
        // start day.
        while (iter.hasNext()) {
            final Event active = iter.next();
            if (active.endDay < event.startDay) {
                colMask &= ~(1L << active.getColumn());
                iter.remove();
            }
        }
        return colMask;
    }

    private static long removeNonAlldayActiveEvents(
            Event event, Iterator<Event> iter, long minDurationMillis, long colMask) {
        long start = event.getStartMillis();
        // Remove the inactive events. An event on the active list
        // becomes inactive when its end time is less than or equal to
        // the current event's start time.
        while (iter.hasNext()) {
            final Event active = iter.next();

            final long duration = Math.max(
                    active.getEndMillis() - active.getStartMillis(), minDurationMillis);
            if ((active.getStartMillis() + duration) <= start) {
                colMask &= ~(1L << active.getColumn());
                iter.remove();
            }
        }
        return colMask;
    }

    public static int findFirstZeroBit(long val) {
        for (int ii = 0; ii < 64; ++ii) {
            if ((val & (1L << ii)) == 0)
                return ii;
        }
        return 64;
    }

    public final boolean intersects(int julianDay, int startMinute,
                                    int endMinute) {
        if (endDay < julianDay) {
            return false;
        }

        if (startDay > julianDay) {
            return false;
        }

        if (endDay == julianDay) {
            if (endTime < startMinute) {
                return false;
            }
            // An event that ends at the start minute should not be considered
            // as intersecting the given time span, but don't exclude
            // zero-length (or very short) events.
            if (endTime == startMinute
                    && (startTime != endTime || startDay != endDay)) {
                return false;
            }
        }

        if (startDay == julianDay && startTime > endMinute) {
            return false;
        }

        return true;
    }

    public String getTitleAndLocation() {
        String text = title.toString();
        // Append the location to the title, unless the title ends with the
        // location (for example, "meeting in building 42" ends with the
        // location).
        if (location != null) {
            String locationString = location.toString();
            if (!text.endsWith(locationString)) {
                text += ", " + locationString;
            }
        }
        return text;
    }

    public void setColumn(int column) {
        mColumn = column;
    }

    public int getColumn() {
        return mColumn;
    }

    public void setMaxColumns(int maxColumns) {
        mMaxColumns = maxColumns;
    }

    public int getMaxColumns() {
        return mMaxColumns;
    }

    public void setStartMillis(long startMillis) {
        this.startMillis = startMillis;
    }

    public long getStartMillis() {
        return startMillis;
    }

    public void setEndMillis(long endMillis) {
        this.endMillis = endMillis;
    }

    public long getEndMillis() {
        return endMillis;
    }

    public boolean drawAsAllday() {
        // Use >= so we'll pick up Exchange allday events
        return allDay || endMillis - startMillis >= DateUtils.DAY_IN_MILLIS;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.scheduleId);
        dest.writeString(this.runDate);
        dest.writeByte(this.isCreateAuthor ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.createdUser, flags);
        dest.writeInt(this.scheduleType);
        dest.writeString(this.circleId);
        dest.writeString(this.easemobGroupId);
        dest.writeString(this.id);
        dest.writeInt(this.color);
        dest.writeString(this.title);
        dest.writeString(this.location);
        dest.writeByte(this.allDay ? (byte) 1 : (byte) 0);
        dest.writeInt(this.startDay);
        dest.writeInt(this.endDay);
        dest.writeInt(this.startTime);
        dest.writeInt(this.endTime);
        dest.writeLong(this.startMillis);
        dest.writeLong(this.endMillis);
        dest.writeInt(this.mColumn);
        dest.writeInt(this.mMaxColumns);
        dest.writeByte(this.hasAlarm ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isRepeating ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.left);
        dest.writeFloat(this.right);
        dest.writeFloat(this.top);
        dest.writeFloat(this.bottom);
        dest.writeParcelable(this.nextRight, flags);
        dest.writeParcelable(this.nextLeft, flags);
        dest.writeParcelable(this.nextUp, flags);
        dest.writeParcelable(this.nextDown, flags);
    }

    public Event() {
    }

    protected Event(Parcel in) {
        this.scheduleId = in.readString();
        this.runDate = in.readString();
        this.isCreateAuthor = in.readByte() != 0;
        this.createdUser = in.readParcelable(ScheduleCreateUser.class.getClassLoader());
        this.scheduleType = in.readInt();
        this.circleId = in.readString();
        this.easemobGroupId = in.readString();
        this.id = in.readString();
        this.color = in.readInt();
        this.title = in.readString();
        this.location = in.readString();
        this.allDay = in.readByte() != 0;
        this.startDay = in.readInt();
        this.endDay = in.readInt();
        this.startTime = in.readInt();
        this.endTime = in.readInt();
        this.startMillis = in.readLong();
        this.endMillis = in.readLong();
        this.mColumn = in.readInt();
        this.mMaxColumns = in.readInt();
        this.hasAlarm = in.readByte() != 0;
        this.isRepeating = in.readByte() != 0;
        this.left = in.readFloat();
        this.right = in.readFloat();
        this.top = in.readFloat();
        this.bottom = in.readFloat();
        this.nextRight = in.readParcelable(Event.class.getClassLoader());
        this.nextLeft = in.readParcelable(Event.class.getClassLoader());
        this.nextUp = in.readParcelable(Event.class.getClassLoader());
        this.nextDown = in.readParcelable(Event.class.getClassLoader());
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
