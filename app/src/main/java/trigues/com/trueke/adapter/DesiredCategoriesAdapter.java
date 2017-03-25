package trigues.com.trueke.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;

/**
 * Created by mbaque on 25/03/2017.
 */

public abstract class DesiredCategoriesAdapter extends RecyclerView.Adapter<DesiredCategoriesAdapter.ViewHolder>{

    private Context context;
    private List<String> desiredCategories;

    public DesiredCategoriesAdapter(Context context, List<String> desiredCategories) {
        this.context = context;
        this.desiredCategories = desiredCategories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.desired_categories_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.text.setText(desiredCategories.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCategoryDeleteButtonClick(desiredCategories.get(position));
            }
        });
    }

    public abstract void onCategoryDeleteButtonClick(String category);

    @Override
    public int getItemCount() {
        return desiredCategories.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        View itemView;

        @BindView(R.id.desired_category_text)
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            ButterKnife.bind(this, itemView);
        }
    }
}
