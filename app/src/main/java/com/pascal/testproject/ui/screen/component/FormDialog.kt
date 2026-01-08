package com.pascal.testproject.ui.screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FormDialog(
    title: String,
    fields: List<FormDialogField>,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmText: String = "Simpan",
    dismissText: String = "Batal"
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                fields.forEach { field ->
                    OutlinedTextField(
                        value = field.value,
                        onValueChange = field.onValueChange,
                        label = { Text(field.label) },
                        leadingIcon = field.icon?.let {
                            { Icon(it, contentDescription = null) }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(confirmText)
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(dismissText)
            }
        }
    )
}

data class FormDialogField(
    val label: String,
    val value: String,
    val icon: ImageVector? = null,
    val onValueChange: (String) -> Unit
)
