package adrianoqueiroz.dev.strongpass.common.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.NetworkWifi
import androidx.compose.material.icons.filled.Public
import androidx.compose.ui.graphics.vector.ImageVector

fun getIconCategory(type: String): ImageVector {
   return when (type) {
      "sites" -> Icons.Default.Public
      "socialNetworks" -> Icons.Default.Groups
      "applications" -> Icons.Default.Apps
      "devices" -> Icons.Default.Devices
      "networks" -> Icons.Default.NetworkWifi
      "files" -> Icons.Default.Folder
      "others" -> Icons.Default.Category
      else -> Icons.Default.Category
   }
}

