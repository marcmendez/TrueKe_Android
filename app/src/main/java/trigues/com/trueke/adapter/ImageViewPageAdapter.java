package trigues.com.trueke.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import trigues.com.trueke.R;

/**
 * Created by mbaque on 24/03/2017.
 */

public class ImageViewPageAdapter extends PagerAdapter {

    private Context context;
    private List<String> imageUrls;

    public ImageViewPageAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.product_details_viewpager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.product_detail_viewpager_imageview);
        Picasso.with(context).load(imageUrls.get(position)).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
