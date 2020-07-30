package com.example.proyecto;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.List;


public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre, tipo, padecimiento, doctor, dias, periodo, horario, dosis;
        ImageView fotomedicamento;
        public Button borrar, editar;
        public ViewHolder(View itemView){
            super(itemView);
            nombre = (TextView)itemView.findViewById(R.id.nombreMedicamento);
            tipo = (TextView)itemView.findViewById(R.id.tipoMedicamento);
            padecimiento = (TextView)itemView.findViewById(R.id.padecimiento);
            doctor = (TextView)itemView.findViewById(R.id.doctor);
            dias = (TextView)itemView.findViewById(R.id.dias);
            horario = (TextView) itemView.findViewById(R.id.horario);
            periodo = (TextView) itemView.findViewById(R.id.periodo);
            fotomedicamento = (ImageView) itemView.findViewById(R.id.fotoMedicamento);
            borrar = (Button) itemView.findViewById(R.id.eliminar);
            editar = (Button) itemView.findViewById(R.id.editar);
            dosis = (TextView)itemView.findViewById(R.id.dosis);
        }
    }

    public List<Medicamento> listaMedicamentos;
    private Context context;

    public RecyclerViewAdaptador(Context context, List<Medicamento> listaMedicamentos){
        this.listaMedicamentos = listaMedicamentos;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicamento, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.nombre.setText(listaMedicamentos.get(position).getNombre());
        holder.tipo.setText(listaMedicamentos.get(position).getTipo());
        holder.padecimiento.setText(listaMedicamentos.get(position).getPadecimiento());
        holder.doctor.setText(listaMedicamentos.get(position).getDoctor());
        holder.dias.setText(listaMedicamentos.get(position).getDias());
        holder.horario.setText(listaMedicamentos.get(position).getHorario());
        holder.periodo.setText(listaMedicamentos.get(position).getPeriodo());
        holder.dosis.setText(listaMedicamentos.get(position).getDosis());
        holder.fotomedicamento.setImageBitmap(listaMedicamentos.get(position).getImgMedicamento());


        holder.borrar.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View view){
                        //Eliminar alarma
                        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "administracion", null, 1);
                        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();
                        Cursor fila = BaseDatabase.rawQuery ("Select * from dias where idmedicamento=" + listaMedicamentos.get(position).getIdMedicamento(), null);
                        fila.moveToFirst();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        listaMedicamentos.get(position).getImgMedicamento().compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] img = stream.toByteArray();
                        if(fila.getInt(2) == 1)
                            cancelAlarm(Integer.parseInt(Integer.toString(listaMedicamentos.get(position).getIdMedicamento()) + "1"));
                        if(fila.getInt(3) == 1)
                            cancelAlarm(Integer.parseInt(Integer.toString(listaMedicamentos.get(position).getIdMedicamento()) + "2"));
                        if(fila.getInt(4) == 1)
                            cancelAlarm(Integer.parseInt(Integer.toString(listaMedicamentos.get(position).getIdMedicamento()) + "3"));
                        if(fila.getInt(5) == 1)
                            cancelAlarm(Integer.parseInt(Integer.toString(listaMedicamentos.get(position).getIdMedicamento()) + "4"));
                        if(fila.getInt(6) == 1)
                            cancelAlarm(Integer.parseInt(Integer.toString(listaMedicamentos.get(position).getIdMedicamento()) + "5"));
                        if(fila.getInt(7) == 1)
                            cancelAlarm(Integer.parseInt(Integer.toString(listaMedicamentos.get(position).getIdMedicamento()) + "6"));
                        if(fila.getInt(8) == 1)
                            cancelAlarm(Integer.parseInt(Integer.toString(listaMedicamentos.get(position).getIdMedicamento()) + "7"));
                        fila.close();
                        BaseDatabase.close();
                        eliminarMedicamento(listaMedicamentos.get(position).getIdMedicamento());

                        //Eliminar alrma
                        listaMedicamentos.remove(position);
                        notifyDataSetChanged();
                    }
        });
        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "administracion", null, 1);
                SQLiteDatabase BaseDatabase = admin.getWritableDatabase();
                Cursor fila = BaseDatabase.rawQuery ("Select * from dias where idmedicamento=" + listaMedicamentos.get(position).getIdMedicamento(), null);
                fila.moveToFirst();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                listaMedicamentos.get(position).getImgMedicamento().compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] img = stream.toByteArray();
                if(fila.getInt(2) == 1)
                    cancelAlarm(Integer.parseInt(Integer.toString(listaMedicamentos.get(position).getIdMedicamento()) + "1"));
                if(fila.getInt(3) == 1)
                    cancelAlarm(Integer.parseInt(Integer.toString(listaMedicamentos.get(position).getIdMedicamento()) + "2"));
                if(fila.getInt(4) == 1)
                    cancelAlarm(Integer.parseInt(Integer.toString(listaMedicamentos.get(position).getIdMedicamento()) + "3"));
                if(fila.getInt(5) == 1)
                    cancelAlarm(Integer.parseInt(Integer.toString(listaMedicamentos.get(position).getIdMedicamento()) + "4"));
                if(fila.getInt(6) == 1)
                    cancelAlarm(Integer.parseInt(Integer.toString(listaMedicamentos.get(position).getIdMedicamento()) + "5"));
                if(fila.getInt(7) == 1)
                    cancelAlarm(Integer.parseInt(Integer.toString(listaMedicamentos.get(position).getIdMedicamento()) + "6"));
                if(fila.getInt(8) == 1)
                    cancelAlarm(Integer.parseInt(Integer.toString(listaMedicamentos.get(position).getIdMedicamento()) + "7"));
                fila.close();
                BaseDatabase.close();
                //listaMedicamentos.remove(position);
                Intent launchactivity = new Intent(context, Editar.class);
                launchactivity.putExtra("idMedicamento", listaMedicamentos.get(position).getIdMedicamento());
                launchactivity.putExtra("idUsuario", listaMedicamentos.get(position).getIdUsuario());
                context.startActivity(launchactivity);
            }
        });
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return listaMedicamentos.size();
    }

    private void cancelAlarm(int RequestCode){
        Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RequestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager)context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    private byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void eliminarMedicamento(int idMedicamento) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "administracion", null, 1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();
        int cantidad = BaseDatabase.delete("medicamento", "idmedicamento=" + idMedicamento, null);

        BaseDatabase.close();

        if (cantidad == 1) {
            Toast.makeText(context, "Artículo eliminado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error en la base de datos", Toast.LENGTH_SHORT).show();
        }
    }

    public void editarMedicamento(int idMedicamento){

    }



}
