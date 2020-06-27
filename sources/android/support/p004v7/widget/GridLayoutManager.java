package android.support.p004v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.p001v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p001v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.support.p004v7.widget.RecyclerView.LayoutManager.LayoutPrefetchRegistry;
import android.support.p004v7.widget.RecyclerView.Recycler;
import android.support.p004v7.widget.RecyclerView.State;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import java.util.Arrays;

/* renamed from: android.support.v7.widget.GridLayoutManager */
public class GridLayoutManager extends LinearLayoutManager {
    private static final boolean DEBUG = false;
    public static final int DEFAULT_SPAN_COUNT = -1;
    private static final String TAG = "GridLayoutManager";
    int[] mCachedBorders;
    final Rect mDecorInsets = new Rect();
    boolean mPendingSpanCountChange = false;
    final SparseIntArray mPreLayoutSpanIndexCache = new SparseIntArray();
    final SparseIntArray mPreLayoutSpanSizeCache = new SparseIntArray();
    View[] mSet;
    int mSpanCount = -1;
    SpanSizeLookup mSpanSizeLookup = new DefaultSpanSizeLookup();

    /* renamed from: android.support.v7.widget.GridLayoutManager$DefaultSpanSizeLookup */
    public static final class DefaultSpanSizeLookup extends SpanSizeLookup {
        public int getSpanSize(int i) {
            return 1;
        }

        public int getSpanIndex(int i, int i2) {
            return i % i2;
        }
    }

    /* renamed from: android.support.v7.widget.GridLayoutManager$LayoutParams */
    public static class LayoutParams extends android.support.p004v7.widget.RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        int mSpanIndex = -1;
        int mSpanSize = 0;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(android.support.p004v7.widget.RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public int getSpanIndex() {
            return this.mSpanIndex;
        }

        public int getSpanSize() {
            return this.mSpanSize;
        }
    }

    /* renamed from: android.support.v7.widget.GridLayoutManager$SpanSizeLookup */
    public static abstract class SpanSizeLookup {
        private boolean mCacheSpanIndices = false;
        final SparseIntArray mSpanIndexCache = new SparseIntArray();

        public abstract int getSpanSize(int i);

        public void setSpanIndexCacheEnabled(boolean z) {
            this.mCacheSpanIndices = z;
        }

        public void invalidateSpanIndexCache() {
            this.mSpanIndexCache.clear();
        }

        public boolean isSpanIndexCacheEnabled() {
            return this.mCacheSpanIndices;
        }

        /* access modifiers changed from: 0000 */
        public int getCachedSpanIndex(int i, int i2) {
            if (!this.mCacheSpanIndices) {
                return getSpanIndex(i, i2);
            }
            int i3 = this.mSpanIndexCache.get(i, -1);
            if (i3 != -1) {
                return i3;
            }
            int spanIndex = getSpanIndex(i, i2);
            this.mSpanIndexCache.put(i, spanIndex);
            return spanIndex;
        }

        /* JADX WARNING: Removed duplicated region for block: B:12:0x002c  */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x003e A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x003f A[RETURN] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int getSpanIndex(int r6, int r7) {
            /*
                r5 = this;
                int r0 = r5.getSpanSize(r6)
                r1 = 0
                if (r0 != r7) goto L_0x0008
                return r1
            L_0x0008:
                boolean r2 = r5.mCacheSpanIndices
                if (r2 == 0) goto L_0x0028
                android.util.SparseIntArray r2 = r5.mSpanIndexCache
                int r2 = r2.size()
                if (r2 <= 0) goto L_0x0028
                int r2 = r5.findReferenceIndexFromCache(r6)
                if (r2 < 0) goto L_0x0028
                android.util.SparseIntArray r3 = r5.mSpanIndexCache
                int r3 = r3.get(r2)
                int r4 = r5.getSpanSize(r2)
                int r3 = r3 + r4
                int r2 = r2 + 1
                goto L_0x002a
            L_0x0028:
                r2 = 0
                r3 = 0
            L_0x002a:
                if (r2 >= r6) goto L_0x003b
                int r4 = r5.getSpanSize(r2)
                int r3 = r3 + r4
                if (r3 != r7) goto L_0x0035
                r3 = 0
                goto L_0x0038
            L_0x0035:
                if (r3 <= r7) goto L_0x0038
                r3 = r4
            L_0x0038:
                int r2 = r2 + 1
                goto L_0x002a
            L_0x003b:
                int r0 = r0 + r3
                if (r0 > r7) goto L_0x003f
                return r3
            L_0x003f:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.p004v7.widget.GridLayoutManager.SpanSizeLookup.getSpanIndex(int, int):int");
        }

        /* access modifiers changed from: 0000 */
        public int findReferenceIndexFromCache(int i) {
            int size = this.mSpanIndexCache.size() - 1;
            int i2 = 0;
            while (i2 <= size) {
                int i3 = (i2 + size) >>> 1;
                if (this.mSpanIndexCache.keyAt(i3) < i) {
                    i2 = i3 + 1;
                } else {
                    size = i3 - 1;
                }
            }
            int i4 = i2 - 1;
            if (i4 < 0 || i4 >= this.mSpanIndexCache.size()) {
                return -1;
            }
            return this.mSpanIndexCache.keyAt(i4);
        }

        public int getSpanGroupIndex(int i, int i2) {
            int spanSize = getSpanSize(i);
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                int spanSize2 = getSpanSize(i5);
                i3 += spanSize2;
                if (i3 == i2) {
                    i4++;
                    i3 = 0;
                } else if (i3 > i2) {
                    i4++;
                    i3 = spanSize2;
                }
            }
            return i3 + spanSize > i2 ? i4 + 1 : i4;
        }
    }

    public GridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setSpanCount(getProperties(context, attributeSet, i, i2).spanCount);
    }

    public GridLayoutManager(Context context, int i) {
        super(context);
        setSpanCount(i);
    }

    public GridLayoutManager(Context context, int i, int i2, boolean z) {
        super(context, i2, z);
        setSpanCount(i);
    }

    public void setStackFromEnd(boolean z) {
        if (!z) {
            super.setStackFromEnd(false);
            return;
        }
        throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
    }

    public int getRowCountForAccessibility(Recycler recycler, State state) {
        if (this.mOrientation == 0) {
            return this.mSpanCount;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
    }

    public int getColumnCountForAccessibility(Recycler recycler, State state) {
        if (this.mOrientation == 1) {
            return this.mSpanCount;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
    }

    public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            super.onInitializeAccessibilityNodeInfoForItem(view, accessibilityNodeInfoCompat);
            return;
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        int spanGroupIndex = getSpanGroupIndex(recycler, state, layoutParams2.getViewLayoutPosition());
        if (this.mOrientation == 0) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(CollectionItemInfoCompat.obtain(layoutParams2.getSpanIndex(), layoutParams2.getSpanSize(), spanGroupIndex, 1, this.mSpanCount > 1 && layoutParams2.getSpanSize() == this.mSpanCount, false));
        } else {
            accessibilityNodeInfoCompat.setCollectionItemInfo(CollectionItemInfoCompat.obtain(spanGroupIndex, 1, layoutParams2.getSpanIndex(), layoutParams2.getSpanSize(), this.mSpanCount > 1 && layoutParams2.getSpanSize() == this.mSpanCount, false));
        }
    }

    public void onLayoutChildren(Recycler recycler, State state) {
        if (state.isPreLayout()) {
            cachePreLayoutSpanMapping();
        }
        super.onLayoutChildren(recycler, state);
        clearPreLayoutSpanMappingCache();
    }

    public void onLayoutCompleted(State state) {
        super.onLayoutCompleted(state);
        this.mPendingSpanCountChange = false;
    }

    private void clearPreLayoutSpanMappingCache() {
        this.mPreLayoutSpanSizeCache.clear();
        this.mPreLayoutSpanIndexCache.clear();
    }

    private void cachePreLayoutSpanMapping() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
            int viewLayoutPosition = layoutParams.getViewLayoutPosition();
            this.mPreLayoutSpanSizeCache.put(viewLayoutPosition, layoutParams.getSpanSize());
            this.mPreLayoutSpanIndexCache.put(viewLayoutPosition, layoutParams.getSpanIndex());
        }
    }

    public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsChanged(RecyclerView recyclerView) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsUpdated(RecyclerView recyclerView, int i, int i2, Object obj) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public android.support.p004v7.widget.RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -1);
        }
        return new LayoutParams(-1, -2);
    }

    public android.support.p004v7.widget.RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    public android.support.p004v7.widget.RecyclerView.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof MarginLayoutParams) {
            return new LayoutParams((MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public boolean checkLayoutParams(android.support.p004v7.widget.RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    public SpanSizeLookup getSpanSizeLookup() {
        return this.mSpanSizeLookup;
    }

    private void updateMeasurements() {
        int i;
        if (getOrientation() == 1) {
            i = (getWidth() - getPaddingRight()) - getPaddingLeft();
        } else {
            i = (getHeight() - getPaddingBottom()) - getPaddingTop();
        }
        calculateItemBorders(i);
    }

    public void setMeasuredDimension(Rect rect, int i, int i2) {
        int i3;
        int i4;
        if (this.mCachedBorders == null) {
            super.setMeasuredDimension(rect, i, i2);
        }
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (this.mOrientation == 1) {
            i4 = chooseSize(i2, rect.height() + paddingTop, getMinimumHeight());
            i3 = chooseSize(i, this.mCachedBorders[this.mCachedBorders.length - 1] + paddingLeft, getMinimumWidth());
        } else {
            i3 = chooseSize(i, rect.width() + paddingLeft, getMinimumWidth());
            i4 = chooseSize(i2, this.mCachedBorders[this.mCachedBorders.length - 1] + paddingTop, getMinimumHeight());
        }
        setMeasuredDimension(i3, i4);
    }

    private void calculateItemBorders(int i) {
        this.mCachedBorders = calculateItemBorders(this.mCachedBorders, this.mSpanCount, i);
    }

    static int[] calculateItemBorders(int[] iArr, int i, int i2) {
        int i3;
        if (!(iArr != null && iArr.length == i + 1 && iArr[iArr.length - 1] == i2)) {
            iArr = new int[(i + 1)];
        }
        int i4 = 0;
        iArr[0] = 0;
        int i5 = i2 / i;
        int i6 = i2 % i;
        int i7 = 0;
        for (int i8 = 1; i8 <= i; i8++) {
            i4 += i6;
            if (i4 <= 0 || i - i4 >= i6) {
                i3 = i5;
            } else {
                i3 = i5 + 1;
                i4 -= i;
            }
            i7 += i3;
            iArr[i8] = i7;
        }
        return iArr;
    }

    /* access modifiers changed from: 0000 */
    public int getSpaceForSpanRange(int i, int i2) {
        if (this.mOrientation != 1 || !isLayoutRTL()) {
            return this.mCachedBorders[i2 + i] - this.mCachedBorders[i];
        }
        return this.mCachedBorders[this.mSpanCount - i] - this.mCachedBorders[(this.mSpanCount - i) - i2];
    }

    /* access modifiers changed from: 0000 */
    public void onAnchorReady(Recycler recycler, State state, AnchorInfo anchorInfo, int i) {
        super.onAnchorReady(recycler, state, anchorInfo, i);
        updateMeasurements();
        if (state.getItemCount() > 0 && !state.isPreLayout()) {
            ensureAnchorIsInCorrectSpan(recycler, state, anchorInfo, i);
        }
        ensureViewSet();
    }

    private void ensureViewSet() {
        if (this.mSet == null || this.mSet.length != this.mSpanCount) {
            this.mSet = new View[this.mSpanCount];
        }
    }

    public int scrollHorizontallyBy(int i, Recycler recycler, State state) {
        updateMeasurements();
        ensureViewSet();
        return super.scrollHorizontallyBy(i, recycler, state);
    }

    public int scrollVerticallyBy(int i, Recycler recycler, State state) {
        updateMeasurements();
        ensureViewSet();
        return super.scrollVerticallyBy(i, recycler, state);
    }

    private void ensureAnchorIsInCorrectSpan(Recycler recycler, State state, AnchorInfo anchorInfo, int i) {
        boolean z = i == 1;
        int spanIndex = getSpanIndex(recycler, state, anchorInfo.mPosition);
        if (z) {
            while (spanIndex > 0 && anchorInfo.mPosition > 0) {
                anchorInfo.mPosition--;
                spanIndex = getSpanIndex(recycler, state, anchorInfo.mPosition);
            }
            return;
        }
        int itemCount = state.getItemCount() - 1;
        int i2 = anchorInfo.mPosition;
        while (i2 < itemCount) {
            int i3 = i2 + 1;
            int spanIndex2 = getSpanIndex(recycler, state, i3);
            if (spanIndex2 <= spanIndex) {
                break;
            }
            i2 = i3;
            spanIndex = spanIndex2;
        }
        anchorInfo.mPosition = i2;
    }

    /* access modifiers changed from: 0000 */
    public View findReferenceChild(Recycler recycler, State state, int i, int i2, int i3) {
        ensureLayoutState();
        int startAfterPadding = this.mOrientationHelper.getStartAfterPadding();
        int endAfterPadding = this.mOrientationHelper.getEndAfterPadding();
        int i4 = i2 > i ? 1 : -1;
        View view = null;
        View view2 = null;
        while (i != i2) {
            View childAt = getChildAt(i);
            int position = getPosition(childAt);
            if (position >= 0 && position < i3 && getSpanIndex(recycler, state, position) == 0) {
                if (((android.support.p004v7.widget.RecyclerView.LayoutParams) childAt.getLayoutParams()).isItemRemoved()) {
                    if (view2 == null) {
                        view2 = childAt;
                    }
                } else if (this.mOrientationHelper.getDecoratedStart(childAt) < endAfterPadding && this.mOrientationHelper.getDecoratedEnd(childAt) >= startAfterPadding) {
                    return childAt;
                } else {
                    if (view == null) {
                        view = childAt;
                    }
                }
            }
            i += i4;
        }
        if (view == null) {
            view = view2;
        }
        return view;
    }

    private int getSpanGroupIndex(Recycler recycler, State state, int i) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanGroupIndex(i, this.mSpanCount);
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout != -1) {
            return this.mSpanSizeLookup.getSpanGroupIndex(convertPreLayoutPositionToPostLayout, this.mSpanCount);
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Cannot find span size for pre layout position. ");
        sb.append(i);
        Log.w(str, sb.toString());
        return 0;
    }

    private int getSpanIndex(Recycler recycler, State state, int i) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getCachedSpanIndex(i, this.mSpanCount);
        }
        int i2 = this.mPreLayoutSpanIndexCache.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout != -1) {
            return this.mSpanSizeLookup.getCachedSpanIndex(convertPreLayoutPositionToPostLayout, this.mSpanCount);
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:");
        sb.append(i);
        Log.w(str, sb.toString());
        return 0;
    }

    private int getSpanSize(Recycler recycler, State state, int i) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanSize(i);
        }
        int i2 = this.mPreLayoutSpanSizeCache.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout != -1) {
            return this.mSpanSizeLookup.getSpanSize(convertPreLayoutPositionToPostLayout);
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:");
        sb.append(i);
        Log.w(str, sb.toString());
        return 1;
    }

    /* access modifiers changed from: 0000 */
    public void collectPrefetchPositionsForLayoutState(State state, LayoutState layoutState, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int i = this.mSpanCount;
        for (int i2 = 0; i2 < this.mSpanCount && layoutState.hasMore(state) && i > 0; i2++) {
            int i3 = layoutState.mCurrentPosition;
            layoutPrefetchRegistry.addPosition(i3, Math.max(0, layoutState.mScrollingOffset));
            i -= this.mSpanSizeLookup.getSpanSize(i3);
            layoutState.mCurrentPosition += layoutState.mItemDirection;
        }
    }

    /* access modifiers changed from: 0000 */
    public void layoutChunk(Recycler recycler, State state, LayoutState layoutState, LayoutChunkResult layoutChunkResult) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean z;
        Recycler recycler2 = recycler;
        State state2 = state;
        LayoutState layoutState2 = layoutState;
        LayoutChunkResult layoutChunkResult2 = layoutChunkResult;
        int modeInOther = this.mOrientationHelper.getModeInOther();
        boolean z2 = modeInOther != 1073741824;
        int i9 = getChildCount() > 0 ? this.mCachedBorders[this.mSpanCount] : 0;
        if (z2) {
            updateMeasurements();
        }
        boolean z3 = layoutState2.mItemDirection == 1;
        int i10 = this.mSpanCount;
        if (!z3) {
            i10 = getSpanIndex(recycler2, state2, layoutState2.mCurrentPosition) + getSpanSize(recycler2, state2, layoutState2.mCurrentPosition);
        }
        int i11 = 0;
        int i12 = 0;
        while (i12 < this.mSpanCount && layoutState2.hasMore(state2) && i10 > 0) {
            int i13 = layoutState2.mCurrentPosition;
            int spanSize = getSpanSize(recycler2, state2, i13);
            if (spanSize <= this.mSpanCount) {
                i10 -= spanSize;
                if (i10 < 0) {
                    break;
                }
                View next = layoutState2.next(recycler2);
                if (next == null) {
                    break;
                }
                i11 += spanSize;
                this.mSet[i12] = next;
                i12++;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Item at position ");
                sb.append(i13);
                sb.append(" requires ");
                sb.append(spanSize);
                sb.append(" spans but GridLayoutManager has only ");
                sb.append(this.mSpanCount);
                sb.append(" spans.");
                throw new IllegalArgumentException(sb.toString());
            }
        }
        if (i12 == 0) {
            layoutChunkResult2.mFinished = true;
            return;
        }
        float f = 0.0f;
        int i14 = i12;
        assignSpans(recycler, state, i12, i11, z3);
        int i15 = 0;
        for (int i16 = 0; i16 < i14; i16++) {
            View view = this.mSet[i16];
            if (layoutState2.mScrapList != null) {
                z = false;
                if (z3) {
                    addDisappearingView(view);
                } else {
                    addDisappearingView(view, 0);
                }
            } else if (z3) {
                addView(view);
                z = false;
            } else {
                z = false;
                addView(view, 0);
            }
            calculateItemDecorationsForChild(view, this.mDecorInsets);
            measureChild(view, modeInOther, z);
            int decoratedMeasurement = this.mOrientationHelper.getDecoratedMeasurement(view);
            if (decoratedMeasurement > i15) {
                i15 = decoratedMeasurement;
            }
            float decoratedMeasurementInOther = (((float) this.mOrientationHelper.getDecoratedMeasurementInOther(view)) * 1.0f) / ((float) ((LayoutParams) view.getLayoutParams()).mSpanSize);
            if (decoratedMeasurementInOther > f) {
                f = decoratedMeasurementInOther;
            }
        }
        if (z2) {
            guessMeasurement(f, i9);
            i15 = 0;
            for (int i17 = 0; i17 < i14; i17++) {
                View view2 = this.mSet[i17];
                measureChild(view2, 1073741824, true);
                int decoratedMeasurement2 = this.mOrientationHelper.getDecoratedMeasurement(view2);
                if (decoratedMeasurement2 > i15) {
                    i15 = decoratedMeasurement2;
                }
            }
        }
        for (int i18 = 0; i18 < i14; i18++) {
            View view3 = this.mSet[i18];
            if (this.mOrientationHelper.getDecoratedMeasurement(view3) != i15) {
                LayoutParams layoutParams = (LayoutParams) view3.getLayoutParams();
                Rect rect = layoutParams.mDecorInsets;
                int i19 = rect.top + rect.bottom + layoutParams.topMargin + layoutParams.bottomMargin;
                int i20 = rect.left + rect.right + layoutParams.leftMargin + layoutParams.rightMargin;
                int spaceForSpanRange = getSpaceForSpanRange(layoutParams.mSpanIndex, layoutParams.mSpanSize);
                if (this.mOrientation == 1) {
                    i8 = getChildMeasureSpec(spaceForSpanRange, 1073741824, i20, layoutParams.width, false);
                    i7 = MeasureSpec.makeMeasureSpec(i15 - i19, 1073741824);
                } else {
                    int makeMeasureSpec = MeasureSpec.makeMeasureSpec(i15 - i20, 1073741824);
                    i7 = getChildMeasureSpec(spaceForSpanRange, 1073741824, i19, layoutParams.height, false);
                    i8 = makeMeasureSpec;
                }
                measureChildWithDecorationsAndMargin(view3, i8, i7, true);
            }
        }
        int i21 = 0;
        layoutChunkResult2.mConsumed = i15;
        if (this.mOrientation == 1) {
            if (layoutState2.mLayoutDirection == -1) {
                int i22 = layoutState2.mOffset;
                i = i22;
                i2 = i22 - i15;
            } else {
                int i23 = layoutState2.mOffset;
                i2 = i23;
                i = i15 + i23;
            }
            i4 = 0;
            i3 = 0;
        } else if (layoutState2.mLayoutDirection == -1) {
            int i24 = layoutState2.mOffset;
            i2 = 0;
            i = 0;
            int i25 = i24 - i15;
            i3 = i24;
            i4 = i25;
        } else {
            i4 = layoutState2.mOffset;
            i3 = i15 + i4;
            i2 = 0;
            i = 0;
        }
        while (i21 < i14) {
            View view4 = this.mSet[i21];
            LayoutParams layoutParams2 = (LayoutParams) view4.getLayoutParams();
            if (this.mOrientation != 1) {
                i2 = getPaddingTop() + this.mCachedBorders[layoutParams2.mSpanIndex];
                i = this.mOrientationHelper.getDecoratedMeasurementInOther(view4) + i2;
            } else if (isLayoutRTL()) {
                int paddingLeft = getPaddingLeft() + this.mCachedBorders[this.mSpanCount - layoutParams2.mSpanIndex];
                i5 = paddingLeft;
                i6 = paddingLeft - this.mOrientationHelper.getDecoratedMeasurementInOther(view4);
                int i26 = i2;
                int i27 = i;
                layoutDecoratedWithMargins(view4, i6, i26, i5, i27);
                if (!layoutParams2.isItemRemoved() || layoutParams2.isItemChanged()) {
                    layoutChunkResult2.mIgnoreConsumed = true;
                }
                layoutChunkResult2.mFocusable |= view4.hasFocusable();
                i21++;
                i4 = i6;
                i2 = i26;
                i3 = i5;
                i = i27;
            } else {
                i4 = getPaddingLeft() + this.mCachedBorders[layoutParams2.mSpanIndex];
                i3 = this.mOrientationHelper.getDecoratedMeasurementInOther(view4) + i4;
            }
            i6 = i4;
            i5 = i3;
            int i262 = i2;
            int i272 = i;
            layoutDecoratedWithMargins(view4, i6, i262, i5, i272);
            if (!layoutParams2.isItemRemoved()) {
            }
            layoutChunkResult2.mIgnoreConsumed = true;
            layoutChunkResult2.mFocusable |= view4.hasFocusable();
            i21++;
            i4 = i6;
            i2 = i262;
            i3 = i5;
            i = i272;
        }
        Arrays.fill(this.mSet, null);
    }

    private void measureChild(View view, int i, boolean z) {
        int i2;
        int i3;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect = layoutParams.mDecorInsets;
        int i4 = rect.top + rect.bottom + layoutParams.topMargin + layoutParams.bottomMargin;
        int i5 = rect.left + rect.right + layoutParams.leftMargin + layoutParams.rightMargin;
        int spaceForSpanRange = getSpaceForSpanRange(layoutParams.mSpanIndex, layoutParams.mSpanSize);
        if (this.mOrientation == 1) {
            i2 = getChildMeasureSpec(spaceForSpanRange, i, i5, layoutParams.width, false);
            i3 = getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), getHeightMode(), i4, layoutParams.height, true);
        } else {
            int childMeasureSpec = getChildMeasureSpec(spaceForSpanRange, i, i4, layoutParams.height, false);
            int childMeasureSpec2 = getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), getWidthMode(), i5, layoutParams.width, true);
            i3 = childMeasureSpec;
            i2 = childMeasureSpec2;
        }
        measureChildWithDecorationsAndMargin(view, i2, i3, z);
    }

    private void guessMeasurement(float f, int i) {
        calculateItemBorders(Math.max(Math.round(f * ((float) this.mSpanCount)), i));
    }

    private void measureChildWithDecorationsAndMargin(View view, int i, int i2, boolean z) {
        boolean z2;
        android.support.p004v7.widget.RecyclerView.LayoutParams layoutParams = (android.support.p004v7.widget.RecyclerView.LayoutParams) view.getLayoutParams();
        if (z) {
            z2 = shouldReMeasureChild(view, i, i2, layoutParams);
        } else {
            z2 = shouldMeasureChild(view, i, i2, layoutParams);
        }
        if (z2) {
            view.measure(i, i2);
        }
    }

    private void assignSpans(Recycler recycler, State state, int i, int i2, boolean z) {
        int i3;
        int i4;
        int i5 = -1;
        int i6 = 0;
        if (z) {
            i5 = i;
            i4 = 0;
            i3 = 1;
        } else {
            i4 = i - 1;
            i3 = -1;
        }
        while (i4 != i5) {
            View view = this.mSet[i4];
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.mSpanSize = getSpanSize(recycler, state, getPosition(view));
            layoutParams.mSpanIndex = i6;
            i6 += layoutParams.mSpanSize;
            i4 += i3;
        }
    }

    public int getSpanCount() {
        return this.mSpanCount;
    }

    public void setSpanCount(int i) {
        if (i != this.mSpanCount) {
            this.mPendingSpanCountChange = true;
            if (i >= 1) {
                this.mSpanCount = i;
                this.mSpanSizeLookup.invalidateSpanIndexCache();
                requestLayout();
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Span count should be at least 1. Provided ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00d8, code lost:
        if (r13 == (r2 > r8)) goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00f5, code lost:
        if (r13 == r10) goto L_0x00bb;
     */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0104  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View onFocusSearchFailed(android.view.View r26, int r27, android.support.p004v7.widget.RecyclerView.Recycler r28, android.support.p004v7.widget.RecyclerView.State r29) {
        /*
            r25 = this;
            r0 = r25
            r1 = r28
            r2 = r29
            android.view.View r3 = r25.findContainingItemView(r26)
            r4 = 0
            if (r3 != 0) goto L_0x000e
            return r4
        L_0x000e:
            android.view.ViewGroup$LayoutParams r5 = r3.getLayoutParams()
            android.support.v7.widget.GridLayoutManager$LayoutParams r5 = (android.support.p004v7.widget.GridLayoutManager.LayoutParams) r5
            int r6 = r5.mSpanIndex
            int r7 = r5.mSpanIndex
            int r5 = r5.mSpanSize
            int r7 = r7 + r5
            android.view.View r5 = super.onFocusSearchFailed(r26, r27, r28, r29)
            if (r5 != 0) goto L_0x0022
            return r4
        L_0x0022:
            r5 = r27
            int r5 = r0.convertFocusDirectionToLayoutDirection(r5)
            r9 = 1
            if (r5 != r9) goto L_0x002d
            r5 = 1
            goto L_0x002e
        L_0x002d:
            r5 = 0
        L_0x002e:
            boolean r10 = r0.mShouldReverseLayout
            if (r5 == r10) goto L_0x0034
            r5 = 1
            goto L_0x0035
        L_0x0034:
            r5 = 0
        L_0x0035:
            r10 = -1
            if (r5 == 0) goto L_0x0040
            int r5 = r25.getChildCount()
            int r5 = r5 - r9
            r11 = -1
            r12 = -1
            goto L_0x0047
        L_0x0040:
            int r5 = r25.getChildCount()
            r11 = r5
            r5 = 0
            r12 = 1
        L_0x0047:
            int r13 = r0.mOrientation
            if (r13 != r9) goto L_0x0053
            boolean r13 = r25.isLayoutRTL()
            if (r13 == 0) goto L_0x0053
            r13 = 1
            goto L_0x0054
        L_0x0053:
            r13 = 0
        L_0x0054:
            int r14 = r0.getSpanGroupIndex(r1, r2, r5)
            r10 = r4
            r8 = -1
            r15 = 0
            r17 = 0
            r18 = -1
        L_0x005f:
            if (r5 == r11) goto L_0x0146
            int r9 = r0.getSpanGroupIndex(r1, r2, r5)
            android.view.View r1 = r0.getChildAt(r5)
            if (r1 != r3) goto L_0x006d
            goto L_0x0146
        L_0x006d:
            boolean r20 = r1.hasFocusable()
            if (r20 == 0) goto L_0x0087
            if (r9 == r14) goto L_0x0087
            if (r4 == 0) goto L_0x0079
            goto L_0x0146
        L_0x0079:
            r21 = r3
            r23 = r8
            r24 = r10
            r22 = r11
            r8 = r17
            r11 = r18
            goto L_0x0132
        L_0x0087:
            android.view.ViewGroup$LayoutParams r9 = r1.getLayoutParams()
            android.support.v7.widget.GridLayoutManager$LayoutParams r9 = (android.support.p004v7.widget.GridLayoutManager.LayoutParams) r9
            int r2 = r9.mSpanIndex
            r21 = r3
            int r3 = r9.mSpanIndex
            r22 = r11
            int r11 = r9.mSpanSize
            int r3 = r3 + r11
            boolean r11 = r1.hasFocusable()
            if (r11 == 0) goto L_0x00a3
            if (r2 != r6) goto L_0x00a3
            if (r3 != r7) goto L_0x00a3
            return r1
        L_0x00a3:
            boolean r11 = r1.hasFocusable()
            if (r11 == 0) goto L_0x00ab
            if (r4 == 0) goto L_0x00b3
        L_0x00ab:
            boolean r11 = r1.hasFocusable()
            if (r11 != 0) goto L_0x00be
            if (r10 != 0) goto L_0x00be
        L_0x00b3:
            r23 = r8
            r24 = r10
            r8 = r17
        L_0x00b9:
            r11 = r18
        L_0x00bb:
            r19 = 1
            goto L_0x0102
        L_0x00be:
            int r11 = java.lang.Math.max(r2, r6)
            int r20 = java.lang.Math.min(r3, r7)
            int r11 = r20 - r11
            boolean r20 = r1.hasFocusable()
            if (r20 == 0) goto L_0x00db
            if (r11 <= r15) goto L_0x00d1
            goto L_0x00b3
        L_0x00d1:
            if (r11 != r15) goto L_0x00f8
            if (r2 <= r8) goto L_0x00d7
            r11 = 1
            goto L_0x00d8
        L_0x00d7:
            r11 = 0
        L_0x00d8:
            if (r13 != r11) goto L_0x00f8
            goto L_0x00b3
        L_0x00db:
            if (r4 != 0) goto L_0x00f8
            r23 = r8
            r24 = r10
            r8 = 1
            r10 = 0
            boolean r16 = r0.isViewPartiallyVisible(r1, r10, r8)
            if (r16 == 0) goto L_0x00fc
            r8 = r17
            if (r11 <= r8) goto L_0x00ee
            goto L_0x00b9
        L_0x00ee:
            if (r11 != r8) goto L_0x00fe
            r11 = r18
            if (r2 <= r11) goto L_0x00f5
            r10 = 1
        L_0x00f5:
            if (r13 != r10) goto L_0x0100
            goto L_0x00bb
        L_0x00f8:
            r23 = r8
            r24 = r10
        L_0x00fc:
            r8 = r17
        L_0x00fe:
            r11 = r18
        L_0x0100:
            r19 = 0
        L_0x0102:
            if (r19 == 0) goto L_0x0132
            boolean r10 = r1.hasFocusable()
            if (r10 == 0) goto L_0x011f
            int r4 = r9.mSpanIndex
            int r3 = java.lang.Math.min(r3, r7)
            int r2 = java.lang.Math.max(r2, r6)
            int r3 = r3 - r2
            r15 = r3
            r17 = r8
            r18 = r11
            r10 = r24
            r8 = r4
            r4 = r1
            goto L_0x013a
        L_0x011f:
            int r8 = r9.mSpanIndex
            int r3 = java.lang.Math.min(r3, r7)
            int r2 = java.lang.Math.max(r2, r6)
            int r3 = r3 - r2
            r10 = r1
            r17 = r3
            r18 = r8
            r8 = r23
            goto L_0x013a
        L_0x0132:
            r17 = r8
            r18 = r11
            r8 = r23
            r10 = r24
        L_0x013a:
            int r5 = r5 + r12
            r3 = r21
            r11 = r22
            r1 = r28
            r2 = r29
            r9 = 1
            goto L_0x005f
        L_0x0146:
            r24 = r10
            if (r4 == 0) goto L_0x014b
            goto L_0x014d
        L_0x014b:
            r4 = r24
        L_0x014d:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p004v7.widget.GridLayoutManager.onFocusSearchFailed(android.view.View, int, android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State):android.view.View");
    }

    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null && !this.mPendingSpanCountChange;
    }
}
