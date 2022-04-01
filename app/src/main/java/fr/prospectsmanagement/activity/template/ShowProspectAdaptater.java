package fr.prospectsmanagement.activity.template;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.prospectsmanagement.R;
import fr.prospectsmanagement.model.Prospect;

public class ShowProspectAdaptater extends RecyclerView.Adapter<ShowProspectAdaptater.ViewHolder> {

    Context context;
    List<Prospect>  prospectList;

    public ShowProspectAdaptater(Context context, List<Prospect> prospectList){
        this.context = context;
        this.prospectList = prospectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowProspectAdaptater.ViewHolder holder, int position) {
        if (prospectList != null && prospectList.size() > 0){
            Prospect model = prospectList.get(position);
            holder.nom_prospect.setText(model.getNom());
            holder.prenom_prospect.setText(model.getPrenom());
            holder.entreprise_prospect.setText(model.getRaisonSocial());
        }else{
            return;
        }
    }

    @Override
    public int getItemCount() {
        if(prospectList != null){
            if (prospectList.size()<=5){
                return prospectList.size();
            }else{
                return 5;
            }
        }else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nom_prospect, prenom_prospect, entreprise_prospect;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            nom_prospect = itemView.findViewById(R.id.nom_prospect);
            prenom_prospect = itemView.findViewById(R.id.prenom_prospect);
            entreprise_prospect = itemView.findViewById(R.id.entreprise_prospect);
        }
    }
}
