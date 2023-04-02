package xyz.lurkyphish2085.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import xyz.lurkyphish2085.myapplication.models.NicePlace;

public class NicePlaceViewModel extends ViewModel {

    private MutableLiveData<List<NicePlace>> nicePlaces;

    public LiveData<List<NicePlace>> getNicePlaces() {
        return nicePlaces;
    }
}
