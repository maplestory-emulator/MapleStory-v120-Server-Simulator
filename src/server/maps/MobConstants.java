/*
 * 輪迴碑石
 */
package server.maps;

import server.maps.MapleMap;
import server.maps.MapleMapObject;
import server.life.MapleMonster;
import constants.MapConstants;

public class MobConstants {
    public static Map<Integer, Integer> reincarnation_mobs = new HashMap<Integer, Integer>();

    static {
        mobs.put(9990026, 2); // 輪迴怪物代碼, 生怪數量倍率
    }

    public static int isMonsterSpawn(MapleMap map) {
        int addition = 1;
        if (MapConstants.isBossMap(map.getId()) || MapConstants.isEventMap(map.getId())) { // 判斷是否為特殊地圖, 輪迴不會在特殊地圖生效
            return 1;
        }
        for (MapleMapObject obj : map.getAllMonstersThreadsafe()) { // 判斷地圖有boss, 回傳倍率1
            final MapleMonster mob = (MapleMonster) obj;
            if (mob.getStats().isBoss() && !isReincarnationMob(mob.getId())) {
                return 1;
            }
        }

        for (Integer mobid : reincarnation_mobs.keySet()) { // 判斷是否有輪迴
            if (map.getMonsterById(mobid) != null) {
                addition *= reincarnation_mobs.get(mobid); // 乘以倍率
            }
        }
        return addition;
    }

    public static boolean isReincarnationMob(int mobid) { // 判斷是否為輪迴怪物
        return reincarnation_mobs.containsKey(mobid);
    }

    public static boolean isSpawnSpeed(MapleMap map) {
        for (Integer mobid : reincarnation_mobs.keySet()) {
            if (map.getMonsterById(mobid) != null) {
                return true;
            }
        }
        return false;
    }
}