package trigues.com.trueke.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trigues.entity.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;

/**
 * Created by Marc on 22/03/2017.
 */

public abstract class UserProductsRecyclerViewAdapter extends RecyclerView.Adapter<UserProductsRecyclerViewAdapter.ViewHolder> {

    private final Context context;
    public List<Product> product;

    public UserProductsRecyclerViewAdapter(Context context, List<Product> lp) {
        this.context = context;
        this.product = lp;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_product_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Picasso.with(context).load(product.get(i).getImages().get(0)).into(viewHolder.itemImage);

        viewHolder.itemTitle.setText(product.get(i).getTitle());
        viewHolder.itemCategory.setText(product.get(i).getProductCategory());

        String priceRange = product.get(i).getMinPrice() + " - " + product.get(i).getMaxPrice() + "€";
        viewHolder.itemPrice.setText(priceRange);

        viewHolder.itemMatchmaking.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onMatchMakingClick();
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
            onItemClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    abstract public void onItemClick();
    abstract public void  onMatchMakingClick();

    class ViewHolder extends RecyclerView.ViewHolder{

        View itemView;

        @BindView(R.id.user_product_list_image)
        public ImageView itemImage;

        @BindView(R.id.user_product_list_title)
        public TextView itemTitle;

        @BindView(R.id.user_product_list_category)
        public TextView itemCategory;

        @BindView(R.id.user_product_list_price)
        public TextView itemPrice;

        @BindView(R.id.user_product_list_matchmaking_launch_button)
        public ImageButton itemMatchmaking;


        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

}
