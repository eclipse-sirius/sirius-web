/*******************************************************************************
 * Copyright (c) 2020 Obeo.
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

import React, { useContext } from 'react';
import { SiriusIcon, Help } from 'icons';
import { Footer } from 'views/Footer';

export const defaultIcon = <SiriusIcon title="" style={{ fill: 'var(--white)' }} />;

const defaultValue = {
  icon: defaultIcon,
  productName: 'Sirius Web',
  userStatus: (
    <a href="https://www.eclipse.org/sirius" rel="noopener noreferrer" target="_blank">
      <Help style={{ fill: 'var(--white)', 'margin-top': 16, width: 32, height: 32 }} title="Help" />
    </a>
  ),
  footer: <Footer />,
};

export const BrandingContext = React.createContext(defaultValue);

export const useBranding = () => {
  const context = useContext(BrandingContext);
  if (context) {
    return context;
  } else {
    return defaultValue;
  }
};
