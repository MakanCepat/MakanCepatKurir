package gonorus.makancepatkurir.customutil;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by USER on 2/9/2017.
 */

public class MyUtilities {

        private Context context;

        public MyUtilities(Context context) {
            this.context = context;
        }

        public MyUtilities() {
        }

        /**
         * @param tanggal      tanggal transaksi
         * @param toFormatDate format tanggal transaksi
         * @return
         */
        public static String getTanggal(String tanggal, String toFormatDate) {
            String newTanggal = "";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            try {
                Date newDate = format.parse(tanggal);
                format = new SimpleDateFormat(toFormatDate);
                newTanggal = format.format(newDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return newTanggal;
        }

        public static String setRupiah(String nominal) {
            DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
            formatRp.setCurrencySymbol("Rp. ");
            formatRp.setMonetaryDecimalSeparator(',');
            formatRp.setGroupingSeparator('.');
            kursIndonesia.setDecimalFormatSymbols(formatRp);
            return kursIndonesia.format(Double.parseDouble(nominal));
        }

        public void showLoading(View view, boolean show) {
            if (show) view.setVisibility(View.VISIBLE);
            else view.setVisibility(View.GONE);
        }

        public static double roundNumber(double value, int places) {
            if (places < 0) throw new IllegalArgumentException();

            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }

        public void showInfoDialog(String message, int styleDialog, final Activity context, final boolean finishActivity) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, styleDialog);
            dialogBuilder.setTitle("Informasi");
            dialogBuilder.setMessage(message);
            dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if (finishActivity) {
                        context.finish();
                    }
                }
            });
            AlertDialog b = dialogBuilder.create();
            b.setCancelable(true);
            b.setCanceledOnTouchOutside(false);
            b.show();
        }

        public double getOngkir(double jarak) {
            double basePrice = 10000;
            double jarakInKm = roundNumber((jarak / 1000), 2);
            if (jarakInKm > 7) {
                double tmp = jarakInKm - 7;
                while (tmp >= 1) {
                    tmp -= 1;
                    basePrice += 1000;
                }
            }
            return basePrice;
        }
}
