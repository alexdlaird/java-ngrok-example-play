/*
 * Copyright (c) 2021-2024 Alex Laird
 *
 * SPDX-License-Identifier: MIT
 */


import com.google.inject.AbstractModule
import services.NgrokApplicationLifecycle

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[NgrokApplicationLifecycle]).asEagerSingleton()
  }
}
