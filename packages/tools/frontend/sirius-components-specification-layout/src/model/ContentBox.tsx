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

import { ContentBoxProps } from './ContentBox.types';

export const ContentBox = ({
  childLayoutStrategy = 'FREE_FORM',
  justifyItems = 'stretch',
  children,
}: ContentBoxProps) => {
  const style: React.CSSProperties = {
    position: 'relative',
    width: '100%',
    height: '100%',
    backgroundColor: `rgb(124, 169, 181)`,
  };

  if (childLayoutStrategy === 'LIST') {
    style.display = 'grid';
    style.gridTemplateColumns = '1fr';
    style.justifyItems = justifyItems;
  }

  return (
    <div id="content-box" style={style}>
      {children}
    </div>
  );
};
