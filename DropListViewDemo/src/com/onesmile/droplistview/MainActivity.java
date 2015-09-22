package com.onesmile.droplistview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mobeta.android.dslv.DragSortListView;
import com.mobeta.android.dslv.DragSortListView.DropListener;

public class MainActivity extends Activity {

	private DragSortListView dsLvContent;
	private List<LampBean> mData;
	private LampAdapter lampAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		dsLvContent = (DragSortListView) findViewById(R.id.dslv_content);
		lampAdapter = new LampAdapter(this, mData);
		// 添加顶部Tip
		View headerView = getLayoutInflater().inflate(R.layout.header_lamp_control, null);
		TextView tvHeader = (TextView) headerView.findViewById(R.id.tv_tipbar);
		tvHeader.setText(lampAdapter.getHeaderItem().getName());
		dsLvContent.addHeaderView(headerView);
		
		dsLvContent.setAdapter(lampAdapter);
		dsLvContent.setDropListener(new DropListener() {
			
			@Override
			public void drop(int from, int to) {
				Log.i("myTag", "drop()    from = " + from + "    to = " + to);
				if(from != to) lampAdapter.moveItem(from, to);
			}
		});
	}

	private void initData() {
		mData = new ArrayList<LampBean>();
		mData.add(new LampBean(2, "aa_Hello", 1));
		mData.add(new LampBean(5, "cc_Phone", 3));
		mData.add(new LampBean(3, "bb_Smile", 2));
		mData.add(new LampBean(6, "cc_Table", 3));
		mData.add(new LampBean(1, "aa_卧室", 1));
		mData.add(new LampBean(4, "bb_Notes", 2));
		mData.add(new LampBean(7, "cc_Mouse", 3));
		mData.add(new LampBean(4, "eee_Notes", 4));
		mData.add(new LampBean(7, "eee_Mouse", 0));
		mData.add(new LampBean(4, "fff_Notes", 0));
		mData.add(new LampBean(7, "ffff_Mouse", 0));
		
		
		mData.add(new LampBean(0, "未分组彩灯", 0,true));
		mData.add(new LampBean(0, "卧室", 1, true));
		mData.add(new LampBean(0, "客厅", 2, true));
		mData.add(new LampBean(0, "厨房", 3, true));
		mData.add(new LampBean(0, "厨房", 4, true));
	}
	
	private class LampAdapter extends MyBaseAdapter<LampBean>{

		public LampAdapter(Context context, List<LampBean> data) {
			super(context, data);
			setData(data);
		}
		
		public void moveItem(int from, int to){
			from ++;	// 因为把Header忽略了，而数据中还有Header，所以加一
			to ++;
			LampBean bean = data.get(from);
			int toGroupId = getToGroupId(from, to);
			bean.setGroupId(toGroupId);
			Utils.removeData(data, from, to);
			notifyDataSetChanged();
		}

		// 获取移动到新组的ID
		private int getToGroupId(int from, int to) {
			if (from > to) {
				to--;	// 当从下往上移动时，忽略To的地方
			}
			// 获取新移动到位置的上一个即可
			return data.get(to).getGroupId();
		}

	    @Override
	    public void setData(List<LampBean> data) {
	        this.data = data;
	        Collections.sort(this.data);
	    }
		
		// 返回第一个Item
		public LampBean getHeaderItem(){
			if (data != null && data.size() > 1) {
				return data.get(0);
			}
			return null;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			convertView = inflater.inflate(R.layout.item_lamp_control, null);
			final LampBean bean = getItem(position);
			if (bean.isTipBar()) {
				convertView.findViewById(R.id.rl_content).setVisibility(View.GONE);
				TextView tvBar = (TextView) convertView.findViewById(R.id.tv_tipbar);
				tvBar.setText(bean.getName());
			} else {
				TextView tvName = (TextView) convertView.findViewById(R.id.tv_wifiname);
				tvName.setText(bean.getName() + "___" + bean.getGroupId());
				convertView.findViewById(R.id.iv_lamp_opt).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Toast.makeText(context, bean.getName() + "___" + bean.getGroupId(), Toast.LENGTH_SHORT).show();
					}
				});
			}
			return convertView;
		}
		
		@Override
		public int getCount() {
			if (data == null) {
				return 0;
			}
			return data.size() - 1;
		}
		
		@Override
		public LampBean getItem(int position) {
			return super.getItem(position + 1);
		}
	}
	
	/**
	 * <com.mobeta.android.dslv.DragSortListView
        android:id="@+id/dslv_content"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:divider="#00000000"
        android:dividerHeight="0dp"
        dslv:collapsed_height="1px"
        dslv:drag_enabled="true"
        dslv:drag_handle_id="@+id/rl_content"	// 设置拖动事件的View ID
        dslv:drag_scroll_start="0.33"
        dslv:drag_start_mode="onLongPress"	 // 设置在长按时触发
        dslv:float_background_color="#99BBBBBB"		// 背景色，在选中后
        dslv:remove_enabled="false"		
        dslv:slide_shuffle_speed="0.3" />
	 */

}
