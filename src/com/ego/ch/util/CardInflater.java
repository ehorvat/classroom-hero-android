package com.ego.ch.util;



import com.ego.ch.tasks.BitmapWorkerTask;
import com.ego.classhero.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA. User: Justin Date: 10/6/13 Time: 12:47 AM
 */
public class CardInflater implements IAdapterViewInflater<CardItemData> {

	ImageView ivCoinSack;
	Context c;
	
	public CardInflater(Context c){
		this.c = c;
	}

	@Override
	public View inflate(final BaseInflaterAdapter<CardItemData> adapter,
			final int pos, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.list_item_card, parent,false);
			holder = new ViewHolder(convertView);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final CardItemData item = adapter.getTItem(pos);
		holder.updateDisplay(item);

		return convertView;
	}

	private class ViewHolder {
		private View m_rootView;
		private TextView m_text1;
		private TextView m_text2;
		private TextView m_text3;

		public ViewHolder(View rootView) {
			m_rootView = rootView;
			m_text1 = (TextView) m_rootView.findViewById(R.id.name);
			m_text2 = (TextView) m_rootView.findViewById(R.id.currentCoins);
			m_text3 = (TextView) m_rootView.findViewById(R.id.lvl);
			ivCoinSack = (ImageView) m_rootView.findViewById(R.id.ivCoinSack);
			final int resId = Constants.imageResIds[2];
			loadBitmap(resId, ivCoinSack, 400, 400);
			rootView.setTag(this);
		}

		public void updateDisplay(CardItemData item) {
			m_text1.setText(item.getName());
			m_text2.setText(String.valueOf(item.currentCoins()));
			m_text3.setText("LvL: " + String.valueOf(item.getLvl()));
		}
		 public void loadBitmap(int resId, ImageView mImageView, int w, int h) {
		        mImageView.setImageResource(resId);
		        BitmapWorkerTask task = new BitmapWorkerTask(c.getApplicationContext(),mImageView, w, h);
		        task.execute(resId);
		    }
	}
}
