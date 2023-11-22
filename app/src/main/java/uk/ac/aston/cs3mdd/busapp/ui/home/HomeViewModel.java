package uk.ac.aston.cs3mdd.busapp.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import uk.ac.aston.cs3mdd.busapp.Module;
import uk.ac.aston.cs3mdd.busapp.MyApplication;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<Integer> currentModuleIndex;
    //private MutableLiveData<List<Module>> modules;
    private List<Module> modules;
    private final MutableLiveData<String> mText;
    private MutableLiveData<Module> currentModule;


    public HomeViewModel() {
        super();
        currentModule = new MutableLiveData<Module>();
        setModuleData(); // only for dummy data
        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");

    }

    private void setModuleData() {
        currentModule.setValue(DataSource.getInstance(MyApplication.getAppContext()).getRealModule());
    }

    public LiveData<Module> getCurrentModule() {
        return currentModule;
    }
    public LiveData<String> getText() {
        return mText;
    }

    public void addModule(Module m) {
        modules.add(m);
//        Log.i("list of modules now " + modules.size());
    }
}