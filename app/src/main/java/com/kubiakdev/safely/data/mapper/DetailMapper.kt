import com.kubiakdev.safely.data.db.entity.DetailEntity
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.ui.password.adapter.item.DetailItem

fun modelToEntity(model: DetailModel) = DetailEntity(
        model.id,
        model.iconResId,
        model.key,
        model.value,
        model.isShown
)

fun itemToModel(entity: DetailItem) = DetailModel(
        entity.id,
        entity.iconResId,
        entity.key,
        entity.value,
        entity.isShown
)

fun modelToItem(model: DetailModel) = DetailItem(
        model.id,
        model.iconResId,
        model.key,
        model.value,
        model.isShown
)