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
import {
  FormBasedView,
  FormContext,
  GQLForm,
  GQLFormRefreshedEventPayload,
  Group,
} from '@eclipse-sirius/sirius-components-forms';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { DiagramFilterFormProps, DiagramFilterViewState } from './DiagramFilterForm.types';
import { useDiagramFilterSubscription } from './useDiagramFilterSubscription';
import { GQLDiagramFilterEventPayload } from './useDiagramFilterSubscription.types';

const useDiagramFilterViewStyles = makeStyles()((theme) => ({
  idle: {
    padding: theme.spacing(1),
  },
  content: {
    padding: theme.spacing(1),
  },
}));

const isFormRefreshedEventPayload = (payload: GQLDiagramFilterEventPayload): payload is GQLFormRefreshedEventPayload =>
  payload && payload.__typename === 'FormRefreshedEventPayload';

export const DiagramFilterForm = ({ editingContextId, diagramId, readOnly }: DiagramFilterFormProps) => {
  const [state, setState] = useState<DiagramFilterViewState>({
    form: null,
  });

  const { payload, complete } = useDiagramFilterSubscription(editingContextId, [diagramId]);
  useEffect(() => {
    if (isFormRefreshedEventPayload(payload)) {
      setState((prevState) => ({ ...prevState, form: payload.form }));
    }
  }, [payload]);

  const { classes } = useDiagramFilterViewStyles();

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

  if (!state.form || complete) {
    return null;
  }
  return (
    <div data-representation-kind="form-diagram-filter">
      <FormContext.Provider
        value={{
          payload: payload,
        }}>
        <FormBasedView
          editingContextId={editingContextId}
          form={state.form}
          readOnly={readOnly}
          postProcessor={extractFirstGroup}
        />
      </FormContext.Provider>
    </div>
  );
};
