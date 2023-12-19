/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import { DelButton, DeleteWrapper } from './DeckCardStyledComponents';

export const DeckDeleteButton = (props) => {
  return (
    <DeleteWrapper {...props}>
      <DelButton>&#10006;</DelButton>
    </DeleteWrapper>
  );
};
