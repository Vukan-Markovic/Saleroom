package vukan.com.apursp.ui.product_image;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vukan.com.apursp.GlideApp;
import vukan.com.apursp.R;
import vukan.com.apursp.firebase.Storage;

public class ProductImageFragment extends Fragment {
    ImageView slika;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        slika = view.findViewById(R.id.slika);
        Storage storage = new Storage();

        if (getArguments() != null)
            GlideApp.with(slika.getContext())
                    .load(storage.getProductImage(ProductImageFragmentArgs.fromBundle(getArguments()).getImageUrl()))
                    .into(slika);
    }
}