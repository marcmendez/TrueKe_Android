package trigues.com.trueke.adapter;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.trigues.entity.Payment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;

/**
 * Created by mbaque on 24/04/2017.
 */

public abstract class UserProfilePaymentMethodsAdapter extends RecyclerView.Adapter<UserProfilePaymentMethodsAdapter.ViewHolder>  {

    private Context context;
    private List<Payment> payments;

    public UserProfilePaymentMethodsAdapter(Context context, List<Payment> payments) {
        this.context = context;
        if (payments == null){
            payments = new ArrayList<>();
        }
        this.payments = payments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_profile_payments_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Payment payment = payments.get(position);
        /**if(payment.getType().contains("Visa")){}
        else if(){}
        else if(){}
        else**/
       // holder.paymentImage.setImageDrawable();
        holder.paymentNumber.setText(payment.getNumber());
        holder.paymentType.setText(payment.getType());

        holder.paymentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPaymentDeleteClick(payment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    abstract public void onPaymentDeleteClick(Payment payment);

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.credit_type_image)
        ImageView paymentImage;
        @BindView(R.id.creditNumber)
        TextView paymentNumber;

        @BindView(R.id.credit_type)
        TextView paymentType;

        @BindView(R.id.user_profile_payment_delete)
        ImageButton paymentDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}