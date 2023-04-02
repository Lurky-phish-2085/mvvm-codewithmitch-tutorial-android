# HOW TO MVVM Android

Based on this video tutorial

[MVVM VIDEO TUTORIAL](https://www.youtube.com/watch?v=ijXjCtCXcN4)

His Github repository of the tutorial

[GITHUB TUTORIAL REPO](https://github.com/mitchtabian/MVVMExample1/tree/master/app/src/main/java/com/codingwithmitch/mvvmrecyclerview)

## Prepare the Dependencies

Add these lines on your `build.gradle` file, within the `dependencies` block

```groovy
dependencies {
    ...

    implementation 'de.hdodenhof:circleimageview:3.1.0'
    implementation 'com.android.support:design:28.0.0'
    implementation 'androidx.recyclerview:recyclerview:1.3.0'
    implementation 'com.github.bumptech.glide:glide:4.15.1'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.15.1'
    implementation 'android.arch.lifecycle:extensions:1.1.1'
    annotationProcessor 'android.arch.lifecycle:compiler:1.1.1'

    ...
}
```

**NOTE:** Some lines of code above are removed for brevity.

## 1. Prepare the packages

With MVVM, we have to separate the Model, View, and the View Model to each
other.

So, create the following packages under your java `src` directory:

1. adapters
2. models
3. repositories
4. viewmodels

## 2. Create a Model class

Design a Model class, that just holds the data and put it under the models
package.

```java
package xyz.lurkyphish2085.myapplication.models;

public class NicePlace {

    private String title;
    private String imageUrl;

    public NicePlace() {}
    public NicePlace(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
```

This model class will hold the data that will be displayed by a view later.

## 3. Create the layout for the view of the item

This would be used to define the look and feel of the items on the view.
Will be used to construct a recycler view later.

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="15dp"
    android:id="@+id/parent_layout">

    <de.hdodenhof.circleimageview.CircleImageView
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:id="@+id/image"
        android:src="@mipmap/ic_launcher"/>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Canada"
        android:id="@+id/image_name"
        android:layout_toRightOf="@+id/image"
        android:textColor="#000"
        android:layout_centerVertical="true"
        android:layout_marginLeft="30dp"
        android:textSize="17sp"/>

</RelativeLayout>
```

**Save** it as `layout_listitem.xml`.

Here is the `activity_main.xml` layout

```java
<android.support.design.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <android.support.v7.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/recycler_view">


    </android.support.v7.widget.RecyclerView>

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <ProgressBar
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/progress_bar"
            android:layout_centerInParent="true"
            android:visibility="gone"/>

    </RelativeLayout>


    <android.support.design.widget.FloatingActionButton
        android:id="@+id/fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="end|bottom"
        android:src="@drawable/baseline_add_24"
        android:layout_margin="16dp" />

</android.support.design.widget.CoordinatorLayout>
```

## 4. Create the RecyclerAdapter 

1. extend it with `RecyclerView.Adapter<RecyclerView.ViewHolder>`
2. press alt + enter and then choose implement methods
3. Declare the following vars and the constructor

```java
    private List<NicePlace> nicePlaces = new ArrayList<>();
    private Context context;

    public RecyclerAdapter(Context context, List<NicePlace> nicePlaces) {
        this.nicePlaces = nicePlaces;
        this.context = context;
    }
```

4. Create a nested class called `ViewHolder`, as this will hold the view
   elements that will be updated by the adapter.

```java
    private class ViewHolder extends RecyclerView.ViewHolder{

	// declare the elements
        private CircleImageView mImage;
        private TextView mName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

	    // instantiate it
            mImage = itemView.findViewById(R.id.image);
            mName = itemView.findViewById(R.id.image_name);
        }
    }
```

5. On the `getItemCount()` make it return the size of the `ArrayList`

6. On the `onCreateViewHolder()` inflate a view object with the item's layout
   and use it to instantiate the `ViewHolder` nested class and return it's
   object.

```java
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }
```

7. On the `onBindViewHolder()` add these lines of code as it will set each
   views based on the models inside the List

```java
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        // Set the name of the `NicePlace`
        viewHolder.name.setText(nicePlaces.get(position).getTitle());

        // Set the image
        RequestOptions defaultOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_background);
        Glide.with(context)
                .setDefaultRequestOptions(defaultOptions)
                .load(nicePlaces.get(position).getImageUrl())
                .into(viewHolder.image);
    }
```

## Prepare the skeleton MainActivity.java

The activity will hold references to each views of it's corresponding layout
`activity_main.xml` as well as initialize the recycler view.

1. Declare the following views and initialize it in the `onCreate()` method.
2. Declare the adapter as well but don't initialize it yet.

```java
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.fab);
        progressBar = findViewById(R.id.progress_bar);
    }
```

3. Create an `initRecyclerView()` method and call it from the `onCreate()`
   method.

```java
    private void initRecyclerView() {
        adapter = new RecyclerAdapter(this, new ArrayList<NicePlace>());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
```

4. Add these two methods that is used for toggling the visibility of the
   progress bar

```java
    private void showProgressBar() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(ProgressBar.GONE);
    }
```


