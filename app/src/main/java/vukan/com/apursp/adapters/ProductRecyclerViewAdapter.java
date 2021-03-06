package vukan.com.apursp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vukan.com.apursp.GlideApp;
import vukan.com.apursp.R;
import vukan.com.apursp.firebase.Storage;
import vukan.com.apursp.models.Product;
import vukan.com.apursp.repository.Repository;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder> {
    private final Storage storage;
    private final Repository repository;
    private List<Product> products;
    private final List<Product> productsCopy = new ArrayList<>();
    final private ListItemClickListener mOnClickListener;

    public ProductRecyclerViewAdapter(ListItemClickListener listener) {
        products = new ArrayList<>();
        repository = new Repository();
        productsCopy.addAll(products);
        storage = new Storage();
        mOnClickListener = listener;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        productsCopy.clear();
        productsCopy.addAll(products);
    }

    public void filter(String text) {
        products.clear();
        if (text.isEmpty()) products.addAll(productsCopy);
        else {
            text = text.toLowerCase();

            for (Product item : productsCopy) {
                if (item.getName().toLowerCase().contains(text) || item.getDescription().toLowerCase().contains(text))
                    products.add(item);
            }
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(position);
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView productName;
        final ImageView productImage;
        final CheckBox star;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productImage = itemView.findViewById(R.id.product_image);
            star = itemView.findViewById(R.id.star);
            productImage.setOnClickListener(this);
            star.setOnClickListener(this);
        }

        void bind(int index) {
            productName.setText(products.get(index).getName());

            GlideApp.with(productImage.getContext())
                    .load(storage.getProductImage(products.get(index).getHomePhotoUrl()))
                    .useAnimationPool(false)
                    .placeholder(R.drawable.ic_image)
                    .dontAnimate()
                    .into(productImage);

            repository.isFavourite(products.get(index).getProductID(), star);
        }

        @Override
        public void onClick(View v) {
            int i = getBindingAdapterPosition();

            if (v instanceof ImageView)
                mOnClickListener.onListItemClick(products.get(i).getProductID());
            else if (v instanceof CheckBox)
                mOnClickListener.onStarItemClick(products.get(i).getProductID(), v);
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(String productID);

        void onStarItemClick(String productID, View view);
    }
}