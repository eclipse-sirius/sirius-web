/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import Button from '@mui/material/Button';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { CreateProjectAreaCard } from './CreateProjectAreaCard';
import { ProjectTemplatesModal } from './ProjectTemplatesModal';
import { ShowAllProjectTemplatesCardState } from './ShowAllProjectTemplatesCard.types';

const useShowAllProjectTemplatesCardStyles = makeStyles()((theme) => ({
  button: {
    padding: '0px',
    margin: '0px',
  },
  projectCardContent: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: theme.palette.divider,
  },
  projectCardIcon: {
    fontSize: theme.spacing(8),
  },
}));

export const ShowAllProjectTemplatesCard = () => {
  const { classes } = useShowAllProjectTemplatesCardStyles();
  const [state, setState] = useState<ShowAllProjectTemplatesCardState>({
    modalDisplayed: null,
  });
  const showAllTemplatesModal = () => setState((prevState) => ({ ...prevState, modalDisplayed: 'SHOW_ALL_TEMPLATES' }));
  const closeModal = () => setState((prevState) => ({ ...prevState, modalDisplayed: null }));
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'showAllProjectTemplatesCard' });
  return (
    <>
      <Button onClick={showAllTemplatesModal} className={classes.button} data-testid="show-all-templates">
        <CreateProjectAreaCard title={t('title')} description={t('description')}>
          <div className={classes.projectCardContent}>
            <MoreHorizIcon className={classes.projectCardIcon} htmlColor="white" />
          </div>
        </CreateProjectAreaCard>
      </Button>

      {state.modalDisplayed === 'SHOW_ALL_TEMPLATES' ? <ProjectTemplatesModal onClose={closeModal} /> : null}
    </>
  );
};
