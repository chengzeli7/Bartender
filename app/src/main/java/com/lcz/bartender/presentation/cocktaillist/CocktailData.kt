package com.lcz.bartender.presentation.cocktaillist

import android.R.attr.name
import com.lcz.bartender.presentation.IngredientAmount

object CocktailData {
    fun getList(): ArrayList<CocktailEntity> {
        return arrayListOf(
            CocktailEntity(
                id = "mojito",
                name = "莫吉托",
                categoryId = "cocktail",
                imageUrl = "https://img95.699pic.com/photo/60011/0337.jpg_wh860.jpg",
                preparationSteps = listOf(
                    "将薄荷叶和青柠角放入杯中轻压出汁",
                    "加入朗姆酒、糖浆和碎冰",
                    "倒入苏打水，搅拌后薄荷叶装饰"
                ),
                ingredients = listOf(
                    IngredientAmount("白朗姆酒", "2", "oz"),
                    IngredientAmount("青柠汁", "1", "oz"),
                    IngredientAmount("新鲜薄荷叶", "8", "片"),
                    IngredientAmount("糖浆", "0.75", "oz"),
                    IngredientAmount("苏打水", "适量", "")
                ),
                flavorDescription = listOf("清爽", "草本", "柑橘", "微甜"),
                difficulty = "简单",
                alcoholStrength = "中度",
                recommendedGlass = "海波杯",
                history = "古巴国酒，源于甘蔗种植园的工人饮品"
            ), CocktailEntity(
                id = "long_island_iced_tea",
                name = "长岛冰茶",
                categoryId = "cocktail",
                imageUrl = "https://www.xiantao.com/uploads/allimg/191227/3-19122G22610.jpg",
                preparationSteps = listOf(
                    "将所有基酒、柠檬汁、糖浆倒入摇酒壶",
                    "加冰摇匀后倒入柯林杯",
                    "可乐补满，柠檬片装饰"
                ),
                ingredients = listOf(
                    IngredientAmount("伏特加", "0.5", "oz"),
                    IngredientAmount("金酒", "0.5", "oz"),
                    IngredientAmount("龙舌兰", "0.5", "oz"),
                    IngredientAmount("朗姆酒", "0.5", "oz"),
                    IngredientAmount("橙皮利口酒", "0.5", "oz"),
                    IngredientAmount("柠檬汁", "1", "oz"),
                    IngredientAmount("糖浆", "0.5", "oz"),
                    IngredientAmount("可乐", "适量", "")
                ),
                flavorDescription = listOf("强烈", "微甜", "柑橘", "茶色伪装"),
                difficulty = "中等",
                alcoholStrength = "高度",
                recommendedGlass = "柯林杯",
                history = "1970年代纽约调酒师发明，以茶色掩饰高酒精度"
            ), CocktailEntity(
                id = "margarita",
                name = "玛格丽特",
                categoryId = "cocktail",
                imageUrl = "https://image.pp918.com/Brand/20220516/20220516190028_8043.jpg",
                preparationSteps = listOf(
                    "杯口用青柠抹湿，蘸盐霜",
                    "龙舌兰、君度、青柠汁加冰摇匀",
                    "过滤倒入玛格丽特杯"
                ),
                ingredients = listOf(
                    IngredientAmount("龙舌兰", "2", "oz"),
                    IngredientAmount("君度橙酒", "1", "oz"),
                    IngredientAmount("青柠汁", "1", "oz"),
                    IngredientAmount("盐边", "1", "圈")
                ),
                flavorDescription = listOf("酸爽", "柑橘", "微咸", "龙舌兰香气"),
                difficulty = "简单",
                alcoholStrength = "中度",
                recommendedGlass = "玛格丽特杯",
                history = "1940年代为纪念逝去恋人Margarita创作"
            ), CocktailEntity(
                id = "gin_tonic",
                name = "金汤力",
                categoryId = "cocktail",
                imageUrl = "https://tse4.mm.bing.net/th/id/OIP.iDKeLXXSmkfQqPD_lA16HAHaEM?rs=1&pid=ImgDetMain&o=7&rm=3",
                preparationSteps = listOf(
                    "杯中装满冰块",
                    "倒入金酒和汤力水",
                    "轻提吧勺混合，青柠角装饰"
                ),
                ingredients = listOf(
                    IngredientAmount("金酒", "2", "oz"),
                    IngredientAmount("汤力水", "4", "oz"),
                    IngredientAmount("青柠角", "1", "块")
                ),
                flavorDescription = listOf("清爽", "微苦", "草本", "气泡感"),
                difficulty = "简单",
                alcoholStrength = "低度",
                recommendedGlass = "高球杯",
                history = "英国殖民时期为预防疟疾开发的药用饮品"
            ), CocktailEntity(
                id = "cosmopolitan",
                name = "大都会",
                categoryId = "cocktail",
                imageUrl = "https://www.moninathome.in/wp-content/uploads/2022/10/Cherry-Cosmopolitan.png",
                preparationSteps = listOf(
                    "所有材料加冰摇至杯壁结霜",
                    "过滤倒入马天尼杯",
                    "橙皮扭花装饰"
                ),
                ingredients = listOf(
                    IngredientAmount("伏特加", "1.5", "oz"),
                    IngredientAmount("君度橙酒", "0.5", "oz"),
                    IngredientAmount("蔓越莓汁", "1", "oz"),
                    IngredientAmount("青柠汁", "0.5", "oz")
                ),
                flavorDescription = listOf("果香", "酸甜", "粉红色", "优雅"),
                difficulty = "中等",
                alcoholStrength = "中度",
                recommendedGlass = "马天尼杯",
                history = "1990年代因《欲望都市》风靡全球的时尚调酒"
            ), CocktailEntity(
                id = "whiskey_sour",
                name = "威士忌酸",
                categoryId = "cocktail",
                imageUrl = "https://tse1.mm.bing.net/th/id/OIP.o2zktbB9-AKkpwsaI8kAVQAAAA?rs=1&pid=ImgDetMain&o=7&rm=3",
                preparationSteps = listOf(
                    "威士忌、柠檬汁、糖浆加冰摇匀",
                    "过滤倒入岩石杯",
                    "樱桃或柠檬角装饰"
                ),
                ingredients = listOf(
                    IngredientAmount("波本威士忌", "2", "oz"),
                    IngredientAmount("柠檬汁", "0.75", "oz"),
                    IngredientAmount("糖浆", "0.5", "oz"),
                    IngredientAmount("蛋清（可选）", "1", "个")
                ),
                flavorDescription = listOf("酸甜", "醇厚", "柑橘", "绵密泡沫"),
                difficulty = "简单",
                alcoholStrength = "中度",
                recommendedGlass = "岩石杯",
                history = "19世纪航海时代预防坏血病的经典酸酒"
            ), CocktailEntity(
                id = "singapore_sling",
                name = "新加坡司令",
                categoryId = "cocktail",
                imageUrl = "https://koreatourinformation.com/wp-content/uploads/2014/12/Fruit-a-palooza.jpg",
                preparationSteps = listOf(
                    "除苏打水外所有材料加冰摇匀",
                    "倒入飓风杯，苏打水补满",
                    "樱桃+菠萝角装饰"
                ),
                ingredients = listOf(
                    IngredientAmount("金酒", "1.5", "oz"),
                    IngredientAmount("樱桃利口酒", "0.5", "oz"),
                    IngredientAmount("菠萝汁", "4", "oz"),
                    IngredientAmount("青柠汁", "0.5", "oz"),
                    IngredientAmount("苏打水", "1", "oz")
                ),
                flavorDescription = listOf("果味", "复合甜香", "热带风情", "粉红色"),
                difficulty = "复杂",
                alcoholStrength = "中度",
                recommendedGlass = "飓风杯",
                history = "1915年新加坡莱佛士酒店原创，东南亚代表调酒"
            ), CocktailEntity(
                id = "pina_colada",
                name = "椰林飘香",
                categoryId = "cocktail",
                imageUrl = "https://tse3.mm.bing.net/th/id/OIP.u6puYH2XCy-yb3z9Fv9WjgHaJu?rs=1&pid=ImgDetMain&o=7&rm=3",
                preparationSteps = listOf(
                    "所有材料加冰用搅拌机打匀",
                    "倒入高脚杯",
                    "菠萝角+樱桃装饰"
                ),
                ingredients = listOf(
                    IngredientAmount("白朗姆酒", "2", "oz"),
                    IngredientAmount("椰奶", "2", "oz"),
                    IngredientAmount("菠萝汁", "3", "oz")
                ),
                flavorDescription = listOf("甜润", "椰香", "热带水果", "绵密"),
                difficulty = "简单",
                alcoholStrength = "中度",
                recommendedGlass = "高脚杯",
                history = "波多黎各国酒，1978年正式确立配方"
            ), CocktailEntity(
                id = "negroni",
                name = "尼格罗尼",
                categoryId = "cocktail",
                imageUrl = "https://pic2.zhimg.com/v2-c548784dae474ff49521db048c62883d_r.jpg",
                preparationSteps = listOf(
                    "所有材料倒入搅拌杯加冰",
                    "搅拌30秒",
                    "过滤倒入岩石杯，橙皮装饰"
                ),
                ingredients = listOf(
                    IngredientAmount("金酒", "1", "oz"),
                    IngredientAmount("金巴利", "1", "oz"),
                    IngredientAmount("甜味美思", "1", "oz")
                ),
                flavorDescription = listOf("苦甜", "草本", "橙香", "浓烈"),
                difficulty = "简单",
                alcoholStrength = "高度",
                recommendedGlass = "岩石杯",
                history = "1919年佛罗伦萨伯爵用金酒改良Americano"
            ), CocktailEntity(
                id = "bloody_mary",
                name = "血腥玛丽",
                categoryId = "cocktail",
                imageUrl = "https://p6.itc.cn/images01/20201117/796970ea8bfc405c94bb2c8a4b103687.png",
                preparationSteps = listOf(
                    "杯口抹柠檬汁蘸盐边",
                    "所有材料加冰摇匀",
                    "过滤倒入海波杯，芹菜杆装饰"
                ),
                ingredients = listOf(
                    IngredientAmount("伏特加", "1.5", "oz"),
                    IngredientAmount("番茄汁", "3", "oz"),
                    IngredientAmount("柠檬汁", "0.5", "oz"),
                    IngredientAmount("伍斯特酱", "2", "dash"),
                    IngredientAmount("辣椒仔", "3", "dash")
                ),
                flavorDescription = listOf("咸鲜", "辛辣", "蔬菜风味", "解酒"),
                difficulty = "中等",
                alcoholStrength = "低度",
                recommendedGlass = "海波杯",
                history = "1920年代巴黎哈利酒吧首创的早午餐鸡尾酒"
            ), CocktailEntity(
                id = "blue_hawaii",
                name = "蓝色夏威夷",
                categoryId = "cocktail",
                imageUrl = "https://www.cdiscount.com/pdt2/2/3/8/1/700x700/auc9354908946238/rw/verre-a-vin-tasses-a-vin-en-verre-de-300ml-2-piec.jpg",
                preparationSteps = listOf(
                    "所有材料加入摇酒壶",
                    "加冰摇匀10秒",
                    "倒入飓风杯，菠萝片装饰"
                ),
                ingredients = listOf(
                    IngredientAmount("白朗姆酒", "1", "oz"),
                    IngredientAmount("蓝橙利口酒", "1", "oz"),
                    IngredientAmount("菠萝汁", "2", "oz"),
                    IngredientAmount("椰奶", "1", "oz"),
                    IngredientAmount("柠檬汁", "0.5", "oz")
                ),
                flavorDescription = listOf("热带", "果香", "甜润", "蓝色"),
                difficulty = "简单",
                alcoholStrength = "中度",
                recommendedGlass = "飓风杯",
                history = "1957年夏威夷调酒师Harry Yee创作，代表太平洋的蓝色"
            ), CocktailEntity(
                id = "dry_martini",
                name = "干马天尼",
                categoryId = "cocktail",
                imageUrl = "https://tse2.mm.bing.net/th/id/OIP.FQpk-CFezRL7_AI4kCwdAQHaFT?rs=1&pid=ImgDetMain&o=7&rm=3",
                preparationSteps = listOf(
                    "金酒和干味美思倒入搅拌杯",
                    "加冰搅拌30秒",
                    "过滤倒入冰镇马天尼杯",
                    "柠檬皮扭花装饰"
                ),
                ingredients = listOf(
                    IngredientAmount("金酒", "2.5", "oz"),
                    IngredientAmount("干味美思", "0.5", "oz"),
                    IngredientAmount("橄榄", "1", "颗")
                ),
                flavorDescription = listOf("干冽", "草本", "清冽", "经典"),
                difficulty = "中等",
                alcoholStrength = "高度",
                recommendedGlass = "马天尼杯",
                history = "19世纪末诞生，被誉为“鸡尾酒之王”"
            ), CocktailEntity(
                id = "cuba_libre",
                name = "自由古巴",
                categoryId = "cocktail",
                imageUrl = "https://tse3.mm.bing.net/th/id/OIP.4XvYP60CU7s9my9POGCZUgAAAA?w=420&h=280&rs=1&pid=ImgDetMain&o=7&rm=3",
                preparationSteps = listOf(
                    "杯中加满冰块",
                    "倒入朗姆酒和青柠汁",
                    "可乐补满，轻轻搅拌",
                    "青柠角装饰"
                ),
                ingredients = listOf(
                    IngredientAmount("黑朗姆酒", "2", "oz"),
                    IngredientAmount("青柠汁", "0.5", "oz"),
                    IngredientAmount("可乐", "适量", "")
                ),
                flavorDescription = listOf("气泡", "甜润", "柑橘", "清爽"),
                difficulty = "简单",
                alcoholStrength = "中度",
                recommendedGlass = "海波杯",
                history = "1900年古巴独立战争时期的爱国饮品"
            ), CocktailEntity(
                id = "daiquiri",
                name = "得其利",
                categoryId = "cocktail",
                imageUrl = "https://tse2.mm.bing.net/th/id/OIP.h22RhAcGfHWM2Oon_SiuaQHaHa?rs=1&pid=ImgDetMain&o=7&rm=3",
                preparationSteps = listOf(
                    "所有材料加入摇酒壶",
                    "加冰摇匀15秒",
                    "过滤倒入鸡尾酒杯"
                ),
                ingredients = listOf(
                    IngredientAmount("白朗姆酒", "2", "oz"),
                    IngredientAmount("青柠汁", "1", "oz"),
                    IngredientAmount("糖浆", "0.75", "oz")
                ),
                flavorDescription = listOf("酸爽", "清甜", "柑橘", "纯净"),
                difficulty = "简单",
                alcoholStrength = "中度",
                recommendedGlass = "鸡尾酒杯",
                history = "19世纪末古巴铁矿工人发明的解渴饮品"
            ), CocktailEntity(
                id = "sidecar",
                name = "边车",
                categoryId = "cocktail",
                imageUrl = "https://tse3.mm.bing.net/th/id/OIP.8YbqUiTXV04RM2sqB4v-TwHaEK?rs=1&pid=ImgDetMain&o=7&rm=3",
                preparationSteps = listOf(
                    "杯口抹柠檬汁蘸糖霜",
                    "所有材料加冰摇匀",
                    "过滤倒入鸡尾酒杯",
                    "橙皮装饰"
                ),
                ingredients = listOf(
                    IngredientAmount("干邑白兰地", "2", "oz"),
                    IngredientAmount("君度橙酒", "1", "oz"),
                    IngredientAmount("柠檬汁", "0.75", "oz")
                ),
                flavorDescription = listOf("酸甜", "橙香", "醇厚", "温暖"),
                difficulty = "中等",
                alcoholStrength = "高度",
                recommendedGlass = "鸡尾酒杯",
                history = "一战时期巴黎丽兹酒店原创，以军用摩托车命名"
            ), CocktailEntity(
                id = "moscow_mule",
                name = "莫斯科骡子",
                categoryId = "cocktail",
                imageUrl = "https://img95.699pic.com/photo/50562/6735.jpg_wh300.jpg!/fh/300/quality/90",
                preparationSteps = listOf(
                    "铜杯中装满冰块",
                    "倒入伏特加和青柠汁",
                    "姜汁啤酒补满，搅拌",
                    "青柠角装饰"
                ),
                ingredients = listOf(
                    IngredientAmount("伏特加", "2", "oz"),
                    IngredientAmount("青柠汁", "0.5", "oz"),
                    IngredientAmount("姜汁啤酒", "4", "oz")
                ),
                flavorDescription = listOf("辛辣", "清爽", "气泡", "姜香"),
                difficulty = "简单",
                alcoholStrength = "低度",
                recommendedGlass = "铜杯",
                history = "1941年好莱坞为推广伏特加而创"
            ), CocktailEntity(
                id = "zombie",
                name = "僵尸",
                categoryId = "cocktail",
                imageUrl = "https://tse2.mm.bing.net/th/id/OIP.QuaPJbHSDiIqUvoI28nlSgHaFj?rs=1&pid=ImgDetMain&o=7&rm=3",
                preparationSteps = listOf(
                    "除装饰外所有材料加入摇酒壶",
                    "加冰摇匀10秒",
                    "倒入僵尸杯，薄荷枝装饰"
                ),
                ingredients = listOf(
                    IngredientAmount("黑朗姆酒", "1.5", "oz"),
                    IngredientAmount("金朗姆酒", "1", "oz"),
                    IngredientAmount("151朗姆酒", "0.5", "oz"),
                    IngredientAmount("百香果汁", "1", "oz"),
                    IngredientAmount("菠萝汁", "1", "oz"),
                    IngredientAmount("青柠汁", "0.5", "oz"),
                    IngredientAmount("杏仁糖浆", "0.5", "oz")
                ),
                flavorDescription = listOf("果味", "浓郁", "高度", "热带"),
                difficulty = "复杂",
                alcoholStrength = "高度",
                recommendedGlass = "僵尸杯",
                history = "1934年好莱坞为宿醉顾客创作的“解酒酒”"
            ), CocktailEntity(
                id = "black_russian",
                name = "黑俄罗斯",
                categoryId = "cocktail",
                imageUrl = "https://img95.699pic.com/photo/60059/1980.jpg_wh300.jpg!/fh/300/quality/90",
                preparationSteps = listOf(
                    "杯中加冰块",
                    "倒入伏特加和咖啡利口酒",
                    "搅拌混合即可"
                ),
                ingredients = listOf(
                    IngredientAmount("伏特加", "2", "oz"),
                    IngredientAmount("咖啡利口酒", "1", "oz")
                ),
                flavorDescription = listOf("醇厚", "咖啡", "微甜", "浓烈"),
                difficulty = "简单",
                alcoholStrength = "高度",
                recommendedGlass = "岩石杯",
                history = "1949年布鲁塞尔为美国大使创作"
            ), CocktailEntity(
                id = "white_russian",
                name = "白俄罗斯",
                categoryId = "cocktail",
                imageUrl = "https://img95.699pic.com/photo/50453/5148.jpg_wh860.jpg",
                preparationSteps = listOf(
                    "杯中加冰块",
                    "倒入伏特加和咖啡利口酒",
                    "淋上鲜奶油，轻轻搅拌"
                ),
                ingredients = listOf(
                    IngredientAmount("伏特加", "2", "oz"),
                    IngredientAmount("咖啡利口酒", "1", "oz"),
                    IngredientAmount("鲜奶油", "0.5", "oz")
                ),
                flavorDescription = listOf("奶油", "咖啡", "顺滑", "甜润"),
                difficulty = "简单",
                alcoholStrength = "高度",
                recommendedGlass = "岩石杯",
                history = "黑俄罗斯变体，1960年代因《谋杀绿脚趾》流行"
            )
        )
    }
}