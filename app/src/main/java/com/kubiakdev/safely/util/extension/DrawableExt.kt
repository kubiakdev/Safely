package com.kubiakdev.safely.util.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.core.graphics.drawable.DrawableCompat

fun Drawable.mutateDrawable(): Drawable = DrawableCompat.wrap(this).mutate()

fun Drawable.tint(context: Context, @ColorRes colorResId: Int): Drawable =
        mutate().apply {
            setTint(context.getColorCompat(colorResId))
            invalidateSelf()
        }

fun Drawable.tint(
        context: Context,
        @AttrRes colorResId: Int,
        resolveRefs: Boolean = true
): Drawable = apply {
    DrawableCompat.setTint(
            mutateDrawable(), TypedValue().apply {
        context.theme.resolveAttribute(colorResId, this, resolveRefs)
    }.data
    )
    invalidateSelf()
}