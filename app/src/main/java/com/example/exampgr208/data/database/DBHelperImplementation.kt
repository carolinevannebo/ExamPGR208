package com.example.exampgr208.data.database

/*
class DBHelperImplementation(private val appDatabase: AppDatabase) : DBHelper {
    override suspend fun getAllRecipes(): List<RecipeItem> = appDatabase.recipeItemDao().getAll()

    override suspend fun getAllRecipeLists(): List<RecipeList> = appDatabase.recipeListDao().getAll()

    override suspend fun insertAllRecipes(recipes: List<RecipeItem>) = appDatabase.recipeItemDao().insertAll(recipes)

    override suspend fun insertAllRecipeLists(lists: List<RecipeList>) = appDatabase.recipeListDao().insertAll(lists)
}
*/
/*
class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createRecipeTableQuery = ("CREATE TABLE " + RECIPE_ITEM_TABLE + " ("
                + RECIPE_ITEM_COL_ID + " TEXT PRIMARY KEY NOT NULL, "
                + RECIPE_ITEM_COL_LABEL + " TEXT, "
                + RECIPE_ITEM_COL_IMAGE + " BLOB, "
                + RECIPE_ITEM_COL_SOURCE + " TEXT, "
                + RECIPE_ITEM_COL_URL + " TEXT, "
                + RECIPE_ITEM_COL_YIELD + " INTEGER, "
                + RECIPE_ITEM_COL_DIET_LABELS + " TEXT, "
                + RECIPE_ITEM_COL_HEALTH_LABELS + " TEXT, "
                + RECIPE_ITEM_COL_CAUTIONS + " TEXT, "
                + RECIPE_ITEM_COL_INGREDIENT_LINES + " TEXT, "
                + RECIPE_ITEM_COL_MEAL_TYPE + " TEXT, "
                + RECIPE_ITEM_COL_CALORIES + " INTEGER "
                + ")")

        val createRecipeListTableQuery = ("CREATE TABLE " + RECIPE_LIST_TABLE + " ("
                + RECIPE_LIST_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RECIPE_LIST_COL_RECIPES + " REFERENCES"
                + ")")

        val createListWithRecipeListsQuery = ("CREATE TABLE " + LIST_WITH_RECIPE_LIST_TABLE + " ("
                + LIST_WITH_RECIPE_LIST_COL_LIST_ID + "INTEGER NOT NULL, "
                + LIST_WITH_RECIPE_LIST_COL_RECIPE_ID + "TEXT NOT NULL, "
                + "FOREIGN KEY(" + LIST_WITH_RECIPE_LIST_COL_LIST_ID + ") REFERENCES "
                + RECIPE_LIST_TABLE + "(" + RECIPE_LIST_COL_ID + "), "
                + "FOREIGN KEY(" + LIST_WITH_RECIPE_LIST_COL_RECIPE_ID + ") REFERENCES "
                + RECIPE_ITEM_TABLE + "(" + RECIPE_ITEM_COL_ID + "), "
                + "PRIMARY KEY(" + LIST_WITH_RECIPE_LIST_COL_LIST_ID + ", "
                + LIST_WITH_RECIPE_LIST_COL_RECIPE_ID + ")"
                + ")")

        val createFavoriteTableQuery = ("CREATE TABLE " + FAVORITE_TABLE + " ("
                + FAVORITE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FAVORITE_COL_RECIPE_NAME + " TEXT REFERENCES " +
                RECIPE_ITEM_TABLE + "(" + RECIPE_ITEM_COL_LABEL + ")" + ", "
                + FAVORITE_COL_RECIPE_ID + " TEXT, "
                + "FOREIGN KEY(" + FAVORITE_COL_RECIPE_ID + ") REFERENCES "
                + RECIPE_ITEM_TABLE + "(" + RECIPE_ITEM_COL_ID + ")"
                + ")")

        val createSearchHistoryTableQuery = ("CREATE TABLE " + SEARCH_HISTORY_TABLE + " ("
                + SEARCH_HISTORY_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SEARCH_HISTORY_COL_QUERY + " TEXT, "
                + SEARCH_HISTORY_COL_LIST_ID + " INTEGER, "
                + "FOREIGN KEY(" + SEARCH_HISTORY_COL_LIST_ID + ") REFERENCES "
                + RECIPE_LIST_TABLE + "(" + RECIPE_LIST_COL_ID + ")"
                + ")")

        db?.execSQL(createRecipeTableQuery)
        db?.execSQL(createRecipeListTableQuery)
        db?.execSQL(createListWithRecipeListsQuery)
        db?.execSQL(createFavoriteTableQuery)
        db?.execSQL(createSearchHistoryTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $RECIPE_ITEM_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $RECIPE_LIST_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $FAVORITE_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $SEARCH_HISTORY_TABLE")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    fun addRecipe(
        uri: String?, label: String?, image: Bitmap?, source: String?,
        url: String?, yield: Int?, dietLabels: String?, healthLabels: String?,
        cautions: String?, ingredientLines: String?, calories: Int?) {

        val values = ContentValues()

        values.put(RECIPE_ITEM_COL_ID, uri)
        values.put(RECIPE_ITEM_COL_LABEL, label)
        values.put(RECIPE_ITEM_COL_IMAGE, image!!.allocationByteCount) //stemmer dette?
        values.put(RECIPE_ITEM_COL_SOURCE, source)
        values.put(RECIPE_ITEM_COL_URL, url)
        values.put(RECIPE_ITEM_COL_YIELD, yield)
        values.put(RECIPE_ITEM_COL_DIET_LABELS, dietLabels)
        values.put(RECIPE_ITEM_COL_HEALTH_LABELS, healthLabels)
        values.put(RECIPE_ITEM_COL_CAUTIONS, cautions)
        values.put(RECIPE_ITEM_COL_INGREDIENT_LINES, ingredientLines)
        values.put(RECIPE_ITEM_COL_CALORIES, calories)

        val db = this.writableDatabase
        db.insert(RECIPE_ITEM_TABLE, null, values)
        db.close()
    }

    fun getRecipe(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $RECIPE_ITEM_TABLE", null)
    }

    companion object {
        private const val DATABASE_NAME = "RecipeHelper.db"
        private const val DATABASE_VERSION = 1

        const val RECIPE_ITEM_TABLE = "Recipe"
        const val RECIPE_ITEM_COL_ID = "Uri"
        const val RECIPE_ITEM_COL_LABEL = "Label"
        const val RECIPE_ITEM_COL_IMAGE = "Image"
        const val RECIPE_ITEM_COL_SOURCE = "Source"
        const val RECIPE_ITEM_COL_URL = "Url"
        const val RECIPE_ITEM_COL_YIELD = "Yield"
        const val RECIPE_ITEM_COL_DIET_LABELS = "DietLabels"
        const val RECIPE_ITEM_COL_HEALTH_LABELS = "HealthLabels"
        const val RECIPE_ITEM_COL_CAUTIONS = "Cautions"
        const val RECIPE_ITEM_COL_INGREDIENT_LINES = "IngredientLines"
        const val RECIPE_ITEM_COL_MEAL_TYPE = "MealType"
        const val RECIPE_ITEM_COL_CALORIES = "Calories"

        const val RECIPE_LIST_TABLE = "RecipeList"
        const val RECIPE_LIST_COL_ID = "Id"
        const val RECIPE_LIST_COL_RECIPES = "Recipes" //denne er vi usikker på

        const val LIST_WITH_RECIPE_LIST_TABLE = "ListWithRecipeLists"
        const val LIST_WITH_RECIPE_LIST_COL_LIST_ID = "ListId"
        const val LIST_WITH_RECIPE_LIST_COL_RECIPE_ID = "ListId"

        const val FAVORITE_TABLE = "Favorites"
        const val FAVORITE_COL_ID = "Id"
        const val FAVORITE_COL_RECIPE_NAME = "RecipeName"
        const val FAVORITE_COL_RECIPE_ID = "RecipeId"

        const val SEARCH_HISTORY_TABLE = "SearchHistory"
        const val SEARCH_HISTORY_COL_ID = "Id"
        const val SEARCH_HISTORY_COL_QUERY = "SearchQuery"
        const val SEARCH_HISTORY_COL_LIST_ID = "ListId"
    }
}*/