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

import { WorkbenchViewComponentProps } from '@eclipse-sirius/sirius-components-core';
import { FormBasedView, GQLForm, Group } from '@eclipse-sirius/sirius-components-forms';
import { makeStyles } from '@material-ui/core/styles';
import { DiagramFilterFormProps } from './DiagramFilterForm.types';
import { useDiagramFilterSubscription } from './useDiagramFilterSubscription';

const useDiagramFilterViewStyles = makeStyles((theme) => ({
  idle: {
    padding: theme.spacing(1),
  },
  content: {
    padding: theme.spacing(1),
  },
}));

export const DiagramFilterForm = ({ editingContextId, diagramId, readOnly }: DiagramFilterFormProps) => {
  const { form } = useDiagramFilterSubscription(editingContextId, [diagramId]);

  const classes = useDiagramFilterViewStyles();

  const extractFirstGroup = (props: WorkbenchViewComponentProps, form: GQLForm): JSX.Element => {
    const group = form.pages[0]?.groups[0];
    if (group) {
      return (
        <div className={classes.content}>
          <Group editingContextId={props.editingContextId} formId={form.id} readOnly={props.readOnly} group={group} />
        </div>
      );
    } else {
      return <div className={classes.content} />;
    }
  };

  if (!form) {
    return null;
  }
  return (
    <FormBasedView
      editingContextId={editingContextId}
      form={form}
      readOnly={readOnly}
      postProcessor={extractFirstGroup}
    />
  );
};
