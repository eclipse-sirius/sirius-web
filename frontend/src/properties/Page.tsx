/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import { makeStyles } from '@material-ui/core/styles';
import { Group } from 'properties/Group';
import { PageProps } from 'properties/Page.types';
import React from 'react';

const usePageStyles = makeStyles((theme) => ({
  page: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(2),
    },
  },
}));

export const Page = ({ editingContextId, formId, page, widgetSubscriptions, setSelection, readOnly }: PageProps) => {
  const classes = usePageStyles();
  return (
    <div className={classes.page}>
      {page.groups.map((group) => {
        return (
          <Group
            editingContextId={editingContextId}
            formId={formId}
            group={group}
            widgetSubscriptions={widgetSubscriptions}
            key={group.id}
            setSelection={setSelection}
            readOnly={readOnly}
          />
        );
      })}
    </div>
  );
};
