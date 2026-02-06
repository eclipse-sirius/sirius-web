/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import Button from '@mui/material/Button';
import { useContext } from 'react';
import { useTranslation } from 'react-i18next';
import { useCurrentProject } from '../../useCurrentProject';
import { QueryResultButtonComponentProps } from './QueryViewExtensionPoints.types';

export const ExportAsCsvButton = ({ objectIds }: QueryResultButtonComponentProps) => {
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const { project } = useCurrentProject();
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'exportResultButton' });
  return (
    <Button
      data-testid="export-csv-button"
      variant="contained"
      color="primary"
      component="a"
      href={encodeURI(
        `${httpOrigin}/api/editingcontexts/${
          project.currentEditingContext?.id
        }/objects?contentType=text/csv&objectIds=${objectIds.join(',')}`
      )}
      type="application/octet-stream">
      {t('exportAsCsv')}
    </Button>
  );
};
