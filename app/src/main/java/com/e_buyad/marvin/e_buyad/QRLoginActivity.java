package com.e_buyad.marvin.e_buyad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.e_buyad.marvin.e_buyad.background_task.IDAsyncTask;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class QRLoginActivity extends AppCompatActivity {

    private SurfaceView cameraView;
    private TextView barcodeInfo;
    public Button tryAgainButton;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrlogin);

        widgetsInit();
        cameraViewCallback();
    }

    private void widgetsInit() {
        cameraView = (SurfaceView) findViewById(R.id.camera_view);
        barcodeInfo = (TextView) findViewById(R.id.code_info);
        tryAgainButton = (Button) findViewById(R.id.try_again);


        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource.Builder(
                this,
                barcodeDetector
        ).setRequestedPreviewSize(640, 480)
        .build();
    }

    private void cameraViewCallback() {
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                //
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    barcodeInfo.post(new Runnable() {
                        public void run() {
                            cameraSource.stop();

                            String username = barcodes.valueAt(0).displayValue;

                            barcodeInfo.setText(username);

                            new IDAsyncTask(QRLoginActivity.this)
                                    .execute(username);
                        }
                    });
                }
            }
        });
    }

    public void tryAgainOnClick(View view) {
        try {
            barcodeInfo.setText(getResources().getString(R.string.username_empty_text));
            tryAgainButton.setVisibility(View.INVISIBLE);
            cameraSource.start(cameraView.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
