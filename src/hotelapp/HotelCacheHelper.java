package hotelapp;

import hotelapp.cache.HotelCache;
import hotelapp.cache.HotelCacheThreadSafe;

/**
 * Created by edgar on 11/3/17.
 */
public final class HotelCacheHelper {
    public static final HotelCache HOTEL_DATA = new HotelCacheThreadSafe();
}
