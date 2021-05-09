package kz.dungeonmasters.core.core_application.presentation.ui.fragments
//
//import android.os.Bundle
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat
//import androidx.databinding.ViewDataBinding
//import com.yandex.mapkit.Animation
//import com.yandex.mapkit.MapKitFactory
//import com.yandex.mapkit.geometry.BoundingBox
//import com.yandex.mapkit.geometry.Point
//import com.yandex.mapkit.layers.ObjectEvent
//import com.yandex.mapkit.map.CameraPosition
//import com.yandex.mapkit.map.MapObjectCollection
//import com.yandex.mapkit.map.MapObjectTapListener
//import com.yandex.mapkit.mapview.MapView
//import com.yandex.mapkit.user_location.UserLocationObjectListener
//import com.yandex.mapkit.user_location.UserLocationView
//import com.yandex.runtime.ui_view.ViewProvider
//import kz.dungeonmasters.core.core_application.R
//import kz.dungeonmasters.core.core_application.presentation.viewModel.CoreLaunchViewModel
//import kz.dungeonmasters.core.core_application.utils.LocationFinderUtilsInterface
//import kz.dungeonmasters.core.core_application.utils.permission.PermissionGeoLocation
//
//abstract class CoreYandexFragment<VB : ViewDataBinding, VM : CoreLaunchViewModel> : CoreFragment<VB, VM>(), UserLocationObjectListener,
//    LocationFinderUtilsInterface {
//
//    protected var mapView: MapView? = null
//    private var mapObjects: MapObjectCollection? = null
//    protected lateinit var permGeoLocation: PermissionGeoLocation
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        MapKitFactory.initialize(requireContext())
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        permGeoLocation = PermissionGeoLocation(context as AppCompatActivity)
//    }
//
//    fun addObjects(
//        list: List<YandexPoint>,
//        needUserIc: Boolean = false
//    ) {
//        val imageView = ImageView(requireContext())
//        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        imageView.layoutParams = params
//        imageView.setImageDrawable(
//            ContextCompat.getDrawable(requireContext(), if (needUserIc) R.drawable.ic_map_user_location else R.drawable.ic_map_location)
//        )
//
//        list.forEach { point ->
//            val addPlaceMark = mapObjects!!.addPlacemark(Point(point.latitude, point.longitude), ViewProvider(imageView))
//            addPlaceMark.userData = point
//        }
//        mapObjects!!.addTapListener(tabObjectTapListener)
//    }
//
//    private val tabObjectTapListener = MapObjectTapListener { mapObject, point ->
//        if (mapObject.userData is YandexPoint) {
//
//            (mapObject.userData as YandexPoint).action.invoke()
//            if ((mapObject.userData as YandexPoint).needMove)
//                mapView?.map?.move(
//                    CameraPosition(Point(point.latitude, point.longitude), 14.0f, 0.0f, 0.0f),
//                    Animation(Animation.Type.SMOOTH, 1f),
//                    null
//                )
//        }
//        true
//    }
//
//    data class YandexPoint(
//        val latitude: Double,
//        val longitude: Double,
//        val needMove: Boolean = true,
//        val action: () -> Unit
//    )
//
//    fun initMapView(mapView: MapView?, latitude: Double, longitude: Double, zoom: Float = 14.0f, ) {
//        this.mapView = mapView
//        mapView?.let {
//            mapObjects = mapView.map.mapObjects.addCollection()
//        }
//        mapView?.map?.move(
//            CameraPosition(Point(latitude, longitude), zoom, 0.0f, 0.0f),
//            Animation(Animation.Type.SMOOTH, 0.5f),
//            null
//        )
//
//    }
//
//    fun callTabObjectTapListener(data: YandexPoint) {
//        data.action.invoke()
//        mapView?.map?.move(
//            CameraPosition(Point(data.latitude, data.longitude), 14.0f, 0.0f, 0.0f),
//            Animation(Animation.Type.SMOOTH, 1f),
//            null
//        )
//    }
//
//    fun moveToCenter(list: List<YandexPoint>) {
//        var minLong = list.first().longitude
//        var maxLong = list.first().longitude
//        var minLat = list.first().latitude
//        var maxLat = list.first().latitude
//
//        list.forEach {
//            when {
//                it.longitude > maxLong -> maxLong = it.longitude
//                it.longitude < minLong -> minLong = it.longitude
//                it.latitude > maxLat -> maxLat = it.latitude
//                it.latitude < minLat -> minLat = it.latitude
//            }
//        }
//
//        val boundingBox = BoundingBox(Point(minLat, minLong), Point(maxLat, maxLong))
//        var cameraPosition = mapView!!.map.cameraPosition(boundingBox)
//        cameraPosition = CameraPosition(cameraPosition.target, cameraPosition.zoom - 0.8f, cameraPosition.azimuth, cameraPosition.tilt)
//        mapView!!.map.move(cameraPosition, Animation(Animation.Type.SMOOTH, 1f), null)
//
//    }
//
//    fun onStartMap(mapView: MapView) {
//        MapKitFactory.getInstance().onStart()
//        mapView.onStart()
//        initMapView(mapView, 50.25, 66.97, zoom = 4.5f)
//    }
//
//    fun onStopMap(mapView: MapView) {
//        mapView.onStop()
//        MapKitFactory.getInstance().onStop()
//    }
//
//
//    override fun onObjectAdded(userLocationView: UserLocationView) {
//
//    }
//
//    override fun onObjectRemoved(p0: UserLocationView) {
//
//    }
//
//    override fun onObjectUpdated(userLocationView: UserLocationView, p1: ObjectEvent) {
//
//    }
//
//}