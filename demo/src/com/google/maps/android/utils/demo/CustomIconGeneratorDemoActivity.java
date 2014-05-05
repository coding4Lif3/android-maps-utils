package com.google.maps.android.utils.demo;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

public class CustomIconGeneratorDemoActivity extends BaseDemoActivity {

    @Override
    protected void startDemo() {
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.8696, 151.2094), 10));

        IconGenerator iconFactory = new IconGenerator(this, R.layout.text_custom_bubble);

        iconFactory.setCustomStyle(R.drawable.car_icon_green, com.google.maps.android.R.style.Bubble_TextAppearance_Light
        );
        addIcon(iconFactory, "Blue style\nasdsadsadasdsad\nsakdjsakldjasl", new LatLng(41.59223, 12.5036633));


        addIcon(iconFactory, "Default", new LatLng(-33.8696, 151.2094));

        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Blue style", new LatLng(-33.9360, 151.2070));

        iconFactory.setRotation(90);
        iconFactory.setCustomStyle(R.drawable.car_icon_red, com.google.maps.android.R.style.Bubble_TextAppearance_Light
        );
        addIcon(iconFactory, "Rotated 90 degrees", new LatLng(-33.8858, 151.096));

        iconFactory.setContentRotation(-90);
        iconFactory.setCustomStyle(R.drawable.car_icon_white, com.google.maps.android.R.style.Bubble_TextAppearance_Dark
        );
        addIcon(iconFactory, "Rotate=90, ContentRotate=-90", new LatLng(-33.9992, 151.098));

        iconFactory.setRotation(0);
        iconFactory.setContentRotation(90);
        iconFactory.setStyle(IconGenerator.STYLE_GREEN);
        addIcon(iconFactory, "ContentRotate=90", new LatLng(-33.7677, 151.244));
    }

    private void addIcon(IconGenerator iconFactory, String text, LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(text))).
                position(position).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

        getMap().addMarker(markerOptions);
    }
}
