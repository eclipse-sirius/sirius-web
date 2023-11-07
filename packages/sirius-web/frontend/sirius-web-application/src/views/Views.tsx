/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { SiriusIcon } from '../core/SiriusIcon';
import { Help } from '../navigationBar/Help';
import { ViewsProps } from './Views.types';
import { ViewsContextValue } from './ViewsContext.types';

const applicationIcon: JSX.Element = <SiriusIcon fontSize="large" />;
const applicationBarMenu: JSX.Element = <Help />;
export const defaultValue: ViewsContextValue = {
  applicationIcon,
  applicationBarMenu,
};
export const Views = ({}: ViewsProps) => {
  return null;
};
