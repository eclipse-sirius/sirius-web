/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

import { SnackbarKey } from 'notistack';

export interface ToastCloseButtonProps {
  toastKey: SnackbarKey;
}

export interface ToastContextInitializerProps {
  children: React.ReactNode;
}

export interface ToastProviderProps {
  children: React.ReactNode;
}
