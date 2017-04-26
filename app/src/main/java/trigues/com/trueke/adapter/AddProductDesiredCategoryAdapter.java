package trigues.com.trueke.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;

/**
 * Created by mbaque on 26/04/2017.
 */

public abstract class AddProductDesiredCategoryAdapter extends RecyclerView.Adapter<AddProductDesiredCategoryAdapter.ViewHolder>  {

    private Context context;
    private List<String> categoryList;

    public AddProductDesiredCategoryAdapter(Context context, List<String> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_product_desired_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.categoryName.setText(categoryList.get(position));

        holder.categoryCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    onDesiredCategoryCheck(categoryList.get(holder.getAdapterPosition()));
                }
                else{
                    onDesiredCategoryUnCheck(categoryList.get(holder.getAdapterPosition()));
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.categoryCheckBox.isChecked()) {
                    holder.categoryCheckBox.setChecked(false);
                }
                else{
                    holder.categoryCheckBox.setChecked(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    abstract public void onDesiredCategoryCheck(String category);

    abstract public void onDesiredCategoryUnCheck(String category);

    class ViewHolder extends RecyclerView.ViewHolder{

        View itemView;

        @BindView(R.id.add_product_desired_category_name)
        TextView categoryName;

        @BindView(R.id.add_product_desired_category_checkbox)
        CheckBox categoryCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
