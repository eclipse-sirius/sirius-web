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

import DeleteIcon from '@material-ui/icons/Delete';
import { CardDeleteIconButton } from '../styled/DeckCardStyledComponents';

export const DeckDeleteButton = (props) => {
  return (
    <CardDeleteIconButton tabIndex={-1} aria-label="deleteCard" {...props}>
      <DeleteIcon fontSize={'small'} />
    </CardDeleteIconButton>
  );
};
