package trigues.com.trueke.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.trigues.entity.Product;

import java.util.List;

import butterknife.BindView;
import trigues.com.trueke.R;

/**
 * Created by Marc on 22/03/2017.
 */

public abstract class ShowProductsAdapter extends RecyclerView.Adapter<ShowProductsAdapter.ViewHolder> {
    private final Context context;

    protected ProgressDialog progressDialog;

    private int number_matches = R.drawable.ic_action_name;
    private Product product;

    public ShowProductsAdapter(Context context) {
        this.context = context;
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_image)
        public ImageView itemImage;

        @BindView(R.id.item_title)
        public TextView itemTitle;

        @BindView(R.id.item_detail)
        public TextView itemDetail;

        @BindView(R.id.matches_image)
        public ImageView matches_image;

        @BindView(R.id.number_matches)
        public TextView number_matches;

        public Product p;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail = (TextView)itemView.findViewById(R.id.item_detail);
            matches_image = (ImageView)itemView.findViewById(R.id.matches_image);
            number_matches = (TextView)itemView.findViewById(R.id.number_matches);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.product_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.itemTitle.setText(product.getTitle());
        viewHolder.itemDetail.setText(product.getDescription());
        //viewHolder.itemImage.setImageResource(images.get(i));
        viewHolder.matches_image.setImageResource(R.drawable.ic_action_name);
        viewHolder.number_matches.setText("3");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
            onItemClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void generateProd(Product product) {
        this.product= product;

    }

    abstract public void onItemClick();



    public void hideProgress() {
        if(progressDialog!=null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    public void onError(String error) {
        hideProgress();
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }
}
