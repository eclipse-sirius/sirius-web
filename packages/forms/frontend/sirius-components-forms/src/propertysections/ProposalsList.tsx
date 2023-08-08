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

import ListItemText from '@material-ui/core/ListItemText';
import MenuItem from '@material-ui/core/MenuItem';
import MenuList from '@material-ui/core/MenuList';
import Popover from '@material-ui/core/Popover';
import Tooltip from '@material-ui/core/Tooltip';
import { ProposalsListProps } from './ProposalsList.types';

export const ProposalsList = ({ anchorEl, proposals, onProposalSelected, onClose }: ProposalsListProps) => {
  return (
    <Popover
      open={true}
      onClose={onClose}
      anchorEl={anchorEl}
      anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'left',
      }}
      transformOrigin={{
        vertical: 'top',
        horizontal: 'left',
      }}>
      <MenuList id="completion-proposals" data-testid="completion-proposals">
        {proposals.map((proposal, index) => (
          <Tooltip
            data-testid={`proposal-${proposal.textToInsert}-${proposal.charsToReplace}`}
            key={index}
            title={proposal.description}
            placement="right">
            <MenuItem button onClick={() => onProposalSelected(proposal)}>
              <ListItemText primary={proposal.textToInsert} />
            </MenuItem>
          </Tooltip>
        ))}
      </MenuList>
    </Popover>
  );
};
